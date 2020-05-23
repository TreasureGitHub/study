package com.ffl.study.spark.scala.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object DistinctOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DistinctOperator")

        // 创建上下文
        val sc = new SparkContext(config)


        val listRDD: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,4,7,8,9,1,2))

        // 使用distinct算子对数据去重，但是因为去重后会导致数据减少，所以可以改变默认的分区数量
        val distinctRDD: RDD[Int] = listRDD.distinct(2)

        distinctRDD.collect().foreach(println)

    }

}
