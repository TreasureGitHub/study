package com.ffl.study.flink.scala.transformation

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/20 21:52
  */
object TestUnion {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val stream1: DataStream[(String, Int)] = env.fromElements(("a",1),("b",1))

        val stream2: DataStream[(String, Int)] = env.fromElements(("b",1),("d",6))

        val stream3: DataStream[(String, Int)] = env.fromElements(("e",7),("f",8))

        val result: DataStream[(String, Int)] = stream1.union(stream2,stream3)

        result.print()

        env.execute("TestUnion")
    }

}
