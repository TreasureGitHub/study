package com.ffl.study.flink.scala.stream

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._

/**
  * @author lff
  * @datetime 2020/10/28 22:31
  */
object StreamWordCount {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val tool: ParameterTool = ParameterTool.fromArgs(args)

        val inputDataStream: DataStream[String] = env.socketTextStream(tool.get("host"), tool.get("port").toInt)

        val resultDataStream: DataStream[(String, Int)] = inputDataStream
          //.flatMap(_.split(" ")).slotSharingGroup("a")  // 在同一个共享组之类的组共享slot   默认的共享组为 default
          .flatMap(_.split(" "))
          //.filter(_.nonEmpty).disableChaining()  // 前面和后面都断开，禁止进行任务链合并
          .filter(_.nonEmpty)
          //.map((_, 1)).startNewChain()        // 开启新的任务链，将filter和map断开
          .map((_,1))
          .keyBy(0)
          .sum(1)

        resultDataStream.print().setParallelism(1)

        env.execute("StreamWordCount")
    }
}
