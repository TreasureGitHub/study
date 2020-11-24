package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object SampleOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("GroupByOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        // map 算子
        val listRDD: RDD[Int] = sc.makeRDD(1 to 10)

        val sampleRDD: RDD[Int] = listRDD.sample(false,1,1)

        sampleRDD.collect().foreach(println)

    }

}
