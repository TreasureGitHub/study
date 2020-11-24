package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object GlomOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("GlomOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        // map 算子
        val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)

        // 将一个分区的数据放入一个数组中
        val glomRDD: RDD[Array[Int]] = listRDD.glom()

        glomRDD.collect().foreach(array => println(array.mkString(",")))

    }

}
