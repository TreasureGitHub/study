package com.ffl.study.flink.scala.time

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
  * @author liufeifei  2020/05/03 19:34
  *
  */
object LateNessDataWindow {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)
        // 设置时间语义
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[StationLog] = env.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        // 定义侧输出流标签
        val lateTag = new OutputTag[StationLog]("late")

        // 引入watermark，数据是乱序的，并且大多数数据延迟2秒
        val result: DataStream[String] = stream
          .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[StationLog](Time.seconds(2)) {
              override def extractTimestamp(t: StationLog): Long = {
                  t.callTime
              }
          })
          .keyBy(_.sid)
          .timeWindow(Time.seconds(10), Time.seconds(5))
          // 设置迟到的数据超出2秒情况怎么办。交给AllowedLateness处理
          // 也分两种情况处理，第一种：允许数据迟到5秒(2~5秒)，再次延迟触发窗口函数。触发的条件是：watermark < endofwindow + allowedLateNess
          // 第二种：迟到的数据在5秒以上，输出到侧流中
          .allowedLateness(Time.seconds(3)) // 允许数据迟到5秒，还可以触发窗口
          .sideOutputLateData(lateTag)
          .aggregate(new MyAggregateCountFunction, new OutputResultWindowFunction)

        // 拆到时间超过5秒的数据，本来需要再计算一下，
        result.getSideOutput(lateTag).print("late")
        result.print("main")

        env.execute()
    }

    class MyAggregateCountFunction extends AggregateFunction[StationLog,Long,Long] {

        override def createAccumulator(): Long = 0

        override def add(in: StationLog, acc: Long): Long = acc + 1

        override def merge(acc: Long, acc1: Long): Long = acc + acc1

        override def getResult(acc: Long): Long = acc
    }

    class OutputResultWindowFunction extends WindowFunction[Long,String,String,TimeWindow]{

        override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[String]): Unit = {
            val value: Long = input.iterator.next()
            val sb = new StringBuilder
            sb.append("窗口的范围：").append(window.getStart).append("-------").append(window.getEnd)
            sb.append("\n")
            sb.append(" 当前的基站ID是：").append(key)
              .append(" 呼叫的数量是：").append(value)

            out.collect(sb.toString())
        }
    }

}