package com.ffl.study.flink.scala.batch

import java.net.URL

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._

/**
  * @author lff
  * @datetime 2020/10/28 22:00
  */
object WordCount {

    def main(args: Array[String]): Unit = {
        // 创建批处理执行环境
        val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

        // 从文件读取数据
        val dataPath: URL = getClass.getResource("/wc.txt")
        val inputDataSet: DataSet[String] = env.readTextFile(dataPath.getPath)


        val resultDataSet: AggregateDataSet[(String, Int)] = inputDataSet
          .flatMap(_.split(" "))
          .map((_, 1))
          .groupBy(0)
          .sum(1)

        resultDataSet.print();

    }

}
