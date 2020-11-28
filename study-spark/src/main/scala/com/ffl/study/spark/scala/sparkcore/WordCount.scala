package com.ffl.study.spark.scala.sparkcore

import com.ffl.study.common.constants.PathConstants
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/18 01:10
  */
object WordCount {

    def main(args: Array[String]): Unit = {

        // sparkconf对象
        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        // 创建spark 上下文
        val sc = new SparkContext(config)

        // 将文件内容一行一行读取出去
        val line: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/input")

        val words: RDD[String] = line.flatMap(_.split(" "))

        val wordToOne: RDD[(String, Int)] = words.map((_, 1))

        val wordSum: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _).sortBy(x => - x._2 )

        val result: Array[(String, Int)] = wordSum.collect()

        result.foreach(println)

        // 关闭连接
        sc.stop()
    }

}
