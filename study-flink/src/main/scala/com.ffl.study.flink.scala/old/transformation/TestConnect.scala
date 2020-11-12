package com.ffl.study.flink.scala.old.transformation

import org.apache.flink.streaming.api.scala.{ConnectedStreams, DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/20 22:05
  */
object TestConnect {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val stream1: DataStream[(String, Int)] = env.fromElements(("a",1),("b",1),("c",1))

        val stream2: DataStream[String] = env.fromElements("e","f","g")

        // 得到 connect stream，里面数据没有真正合并
        val stream3: ConnectedStreams[(String, Int), String] = stream1.connect(stream2)

        // 需要使用comap或colFlatMap
        val result: DataStream[(String, Int)] = stream3.map(
            t1 => (t1._1, t1._2),
            t2 => (t2, 0)
        )

        result.print()

        env.execute("TestConnect")
    }

}
