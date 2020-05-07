package com.ffl.study.flink.scala.time

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
  * @author liufeifei  2020/05/03 16:56
  *
  *         每隔5秒统计最近10秒内每个基站中通话时间最长的一次通话发生的时间、主叫号码，被叫号码，通话时长 并且输出当前发生时间范围
  *
  */
object MaxLongCallTime2 {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)
        // 设置时间语义
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        env.getConfig.setAutoWatermarkInterval(100) // 周期引入waterwark的设置，默认就是100ms

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[StationLog] = env.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        // 引入watermark(数据是乱序的），通过观察延迟的时间是3秒，采用周期性的watermark引入，默认为100ms
        stream
          // 代码有两种写法，
          // 第一种,直接使用接口的实现类
          //          .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[StationLog](Time.seconds(3)) {
          //
          //            // 设置eventtime
          //            override def extractTimestamp(element: StationLog): Long = {
          //                element.callTime // 就是eventtime
          //            }
          //        })
          .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[StationLog]() {

            private var maxEventTime: Long = _

            // 延迟时间 3秒
            private val DELAY_TIME: Long = 3000

            // 提取 时间
            override def extractTimestamp(t: StationLog, l: Long): Long = {
                maxEventTime = maxEventTime.max(t.callTime)
                t.callTime
            }

            // 得到 watermark
            override def getCurrentWatermark: Watermark = {
                new Watermark(maxEventTime - DELAY_TIME) // 延迟时间为
            }
        })

          .filter(_.callType.equals("success"))
          .keyBy(_.sid)
          .timeWindow(Time.seconds(10), Time.seconds(5))
          .reduce(new MyReduceFunction, new ReturnMaxTimeWindowFunction)
          .print()

        env.execute("MaxLongCallTime")

    }

    class MyReduceFunction extends ReduceFunction[StationLog] {

        // 增量聚合
        override def reduce(t: StationLog, t1: StationLog): StationLog = {
            if (t.duration > t1.duration) t else t1
        }
    }

    class ReturnMaxTimeWindowFunction extends WindowFunction[StationLog, String, String, TimeWindow] {

        // 在窗口触发的时候才调用一次
        override def apply(key: String, window: TimeWindow, input: Iterable[StationLog], out: Collector[String]): Unit = {
            val value: StationLog = input.iterator.next()
            val sb = new StringBuilder
            sb.append("窗口范围:").append((window.getStart)).append("------").append(window.getEnd)
            sb.append("\n")
            sb.append(" sid").append(key)
            sb.append(" 呼叫时间：").append(value.callTime)
              .append(" 主叫号码：").append(value.callOut)
              .append(" 被叫号码：").append(value.callIn)
              .append(" 通话时长：").append(value.duration)

            out.collect(sb.toString())

        }
    }


}
