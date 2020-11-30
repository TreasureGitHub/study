package com.ffl.study.spark.scala.sparkcore.lineage

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/28 23:34
  */
object WordCount {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
        val sc = new SparkContext(conf)

        val dataRDD: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/wc_input")
        val dataRDD1: RDD[(String, Int)] = dataRDD
          .flatMap(_.split(StringConstants.SPACE))
          .map((_, 1))
          .reduceByKey(_ + _)
          .sortBy(_._2, false)

        println(dataRDD1.toDebugString)

        println(dataRDD1.dependencies)

        dataRDD1.collect().foreach(println)

        //println(dataRDD1.collect())

        sc.stop
    }

}
