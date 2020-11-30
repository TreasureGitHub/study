package com.ffl.study.spark.scala.sparkcore.cache

import com.ffl.study.common.constants.PathConstants
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @author lff
  * @datetime 2020/11/29 15:25
  */
object CheckPoint {

    def main(args: Array[String]): Unit = {

        // 可以在resource目录中看到相关文件
        val conf: SparkConf = new SparkConf().setAppName("CheckPoint").setMaster("local[*]")
        val sc = new SparkContext(conf)
        sc.setCheckpointDir(PathConstants.SPARK_RES + "/checkpoint/ck")

        // 将文件内容一行一行读取出去
        val line: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/wc_input")

        val words: RDD[String] = line.flatMap(_.split(" "))

        val wordToOne: RDD[(String, Int)] = words.map((_, 1))

        // 查看血缘信息
        println(wordToOne.toDebugString)
        println("-----------------checkpoint前-----------------------")

        val wordSum: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)

        wordSum.checkpoint()

        val sortBy: RDD[(String, Int)] = wordSum.sortBy(x => - x._2 )
        // 查看血缘信息,checkpoint后 血缘关系从 wordSum(31行开始)，可以注释掉 wordSum 参考对比
        println(sortBy.toDebugString)
        println("-----------------checkpoint后-----------------------")

        val result: Array[(String, Int)] = sortBy.collect()

        result.foreach(println)

        sc.stop()
    }
}
