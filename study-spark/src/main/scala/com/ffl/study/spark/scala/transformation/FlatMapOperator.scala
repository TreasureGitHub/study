package com.ffl.study.spark.scala.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object FlatMapOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("FlatMapOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        // map 算子
        val listRdd: RDD[List[Int]] = sc.makeRDD(Array(List(1,2),List(3,4)))

        // 所有RDD算子的计算功能全部由Executor执行

        val flatMapRdd: RDD[Int] = listRdd.flatMap( datas => datas)


        flatMapRdd.collect().foreach(println)

    }

}
