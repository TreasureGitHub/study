package com.ffl.study.flink.scala.old.window

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
  * @author liufeifei  2020/05/01 11:19
  *
  * 每隔3秒钟统计最近5秒内的日志数量
  *
  */
object TestProcessTimeFunctionByWindow {

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
          .window(TumblingProcessingTimeWindows.of(Time.seconds(5))) // 滑动窗口
          .process(new ProcessWindowFunction[(String, Int), (String, Long), String, TimeWindow] {

            override def process(key: String, context: Context, elements: Iterable[(String, Int)], out: Collector[(String, Long)]): Unit = {
                println("----------process---------")
                // 整个窗口的数据保存到迭代器中了，里面有很多行数据，Iterable的size就是总行数
                out.collect(key,elements.size)
            }
        })

        result.print()

        env.execute("TestReduceFunctionByWindow");

    }



}
