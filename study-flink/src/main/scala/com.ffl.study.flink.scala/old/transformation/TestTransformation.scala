package com.ffl.study.flink.scala.old.transformation

import com.ffl.study.flink.scala.old.source.{MyCustomSource, StationLog}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/19 20:07
  */
object TestTransformation {

    // 从自定义的数据源中读取基站通话日志，统计每一个基站通话成功的总时长是多少秒
    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val stream: DataStream[StationLog] = env.addSource(new MyCustomSource)

        val result: DataStream[(String, Long)] = stream.filter(_.callType.equals("success"))
          .map(log => (log.sid, log.duration)) // 转换为二元组
          .keyBy(0)
          .reduce((t1, t2) => (t1._1, t1._2 + t2._2))

        result.print()

        env.execute("TestTransformation")
    }

}
