package com.ffl.study.flink.scala.window

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author liufeifei  2020/05/01 11:19
  *
  *         每隔5秒钟统计每隔基站的日志数量
  *
  */
object TestReduceFunctionByWindow {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[StationLog] = env.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        val result: DataStream[(String, Int)] = stream
          .map((log) => (log.sid, 1))
          .keyBy(_._1)
          .timeWindow(Time.seconds(5))
          .reduce((t1, t2) => {
              println("test")
              (t1._1, t1._2 + t2._2)
          })

        result.print()

        env.execute("TestReduceFunctionByWindow");

    }

}
