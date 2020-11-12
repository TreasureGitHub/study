package com.ffl.study.flink.scala.old.state

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * @author lff
  * @datetime 2020/04/04 22:38
  *
  *           flink中流计算word count
  */
object TestSavePoint {

    def main(args: Array[String]): Unit = {

        //1. 初始化流计算环境
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        // 修改并行度
        //env.setParallelism(1)

        // 2.导入隐式转换
        import org.apache.flink.streaming.api.scala._

        // 3.读取数据，读取socket中的数据
        val stream: DataStream[String] = env.socketTextStream("localhost", 8888)
          .uid("socket001")

        // 4.转换和处理数据
        val result: DataStream[(String, Int)] =
            stream.flatMap(_.split(" "))
              .uid("flatmap001")
              .map((_, 1))
              .uid("map001")
              .setParallelism(2)
              .keyBy(0)   // 分组算子
              .sum(1)
              .uid("sum001")
              .setParallelism(2)   // 聚合算子

        // 打印结果
        result.print("result")

        // 启动流计算程序
        env.execute("WordCount")
    }

}
