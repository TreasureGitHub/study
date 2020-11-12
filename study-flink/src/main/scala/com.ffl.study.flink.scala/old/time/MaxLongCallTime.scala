package com.ffl.study.flink.scala.old.time

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
  * @author liufeifei  2020/05/03 16:56
  *
  * 每隔5秒统计最近10秒内每个基站中通话时间最长的一次通话发生的时间、主叫号码，被叫号码，通话时长 并且输出当前发生时间范围
  *
  * 试验数据，如果多个watermark，则取最小值watermark
  *
  * station_1,18600003796,18900008373,success,1587395188257,14
  * station_1,18600003796,18900008373,success,1587395189000,2
  * station_2,18600003796,18900008373,success,1587395190000,2
  * station_1,18600003796,18900008373,success,1587395190000,2
  * station_2,18600003796,18900008373,success,1587395195000,2
  * station_1,18600003796,18900008373,success,1587395195000,10
  *
  */
object MaxLongCallTime {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(2)
        // 设置时间语义
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[StationLog] = env.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        // 引入watermark(数据是有序的)
        stream
          .assignAscendingTimestamps(_.callTime)  // 参数中指定eventtime 具体的值
          .filter(_.callType.equals("success"))
          .keyBy(_.sid)
          .timeWindow(Time.seconds(10),Time.seconds(5))
          .reduce(new MyReduceFunction,new ReturnMaxTimeWindowFunction)
          .print()

        env.execute("MaxLongCallTime")

    }

    class MyReduceFunction extends ReduceFunction[StationLog] {

        // 增量聚合
        override def reduce(t: StationLog, t1: StationLog): StationLog = {
            if(t.duration > t1.duration) t else t1
        }
    }

    class ReturnMaxTimeWindowFunction extends WindowFunction[StationLog,String,String,TimeWindow] {

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
