package com.ffl.study.flink.scala.source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * @author lff
  * @datetime 2020/04/14 23:35
  */
object HDFSFileSource {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        //  读取 hdfs文件
        val stream: DataStream[String] = env.readTextFile("hdfs://localhost:9000/wc/word.txt")

        val result: DataStream[(String, Int)] = stream.flatMap(_.split(" "))
          .map((_, 1))
          .keyBy(0)
          .sum(1)

        result.print()

        env.execute("HDFSFileSource")
    }

}
