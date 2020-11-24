package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object CoalesceOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DistinctOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        val listRDD: RDD[Int] = sc.makeRDD(1 to 16,4)

        println("缩减分区前 = " + listRDD.partitions.size)

        val coalesceRDD: RDD[Int] = listRDD.coalesce(3)

        println("缩减分区后 = " + coalesceRDD.partitions.size)
    }

}
