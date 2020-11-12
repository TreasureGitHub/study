package com.ffl.study.flink.scala.old.window

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
  * @author liufeifei  2020/05/01 11:19
  *
  * 每隔3秒钟统计最近5秒内的日志数量
  *
  */
object TestAggerateFunctionByWindow {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[StationLog] = env.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        val result: DataStream[(String, Long)] = stream
          .map(log => (log.sid, 1))
          .keyBy(_._1)
          .window(SlidingProcessingTimeWindows.of(Time.seconds(5), Time.seconds(3))) // 滑动窗口
          .aggregate(new MyAggregateionFunction, new MyWindowFunction)

        result.print()

        env.execute("TestReduceFunctionByWindow");

    }

    /**
      * add 方法 来一次方法执行一次
      * get result 在窗口结束的时候执行一次
      *
      */
    class MyAggregateionFunction extends AggregateFunction[(String,Int),Long,Long] {


        // 初始化累加器
        override def createAccumulator(): Long = 0

        override def add(in: (String, Int), acc: Long): Long = acc + in._2

        // 多个分区合并
        override def merge(acc: Long, acc1: Long): Long = acc + acc1

        // 返回结果
        override def getResult(acc: Long): Long = acc
    }

    // windown function的输入数据来自于Aggregator
    // 在窗口结束的时候，先执行 AggregateFunction 的getResult方法，然后再执行 apply方法
    class MyWindowFunction extends WindowFunction[Long,(String,Long),String,TimeWindow] {

        override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[(String, Long)]): Unit = {
            out.collect(key,input.iterator.next()) // next得到第一个值，迭代器中只有一个值
        }
    }

}
