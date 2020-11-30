package com.ffl.study.spark.scala.sparkcore.partition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/29 16:04
  */
object DefaultPartitionTest {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setAppName("OperatorValue").setMaster("local[*]")

        val sc = new SparkContext(config)
        val dataRDD: RDD[String] = sc.makeRDD(List("a", "b", "a", "c"))

        val result: Array[(String, Int)] = dataRDD.map((_, 1))
          .reduceByKey(_ + _)
          .collect()

        println(dataRDD.partitioner)
        result.foreach(println)

        sc.stop()

    }

}
