package com.ffl.study.flink.scala.old.batch

import java.net.URL

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._

/**
  * @author lff
  * @datetime 2020/04/06 16:30
  */
object BatchWordCount {

    def main(args: Array[String]): Unit = {
        val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

        // 使用相对路径来得到完整的文件路径
        val dataPath: URL = getClass.getResource("/wc_input/wc.txt")

        val data: DataSet[String] = env.readTextFile(dataPath.getPath)

        data.flatMap(_.split(" "))
          .map((_,1))
          .groupBy(0)
          .sum(1)
          .print()

    }

}