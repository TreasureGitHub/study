package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object MapOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark02_Oper1")

        // 创建上下文
        val sc = new SparkContext(config)

        // map 算子
        val listRdd: RDD[Int] = sc.makeRDD(1 to 10)

        // 所有RDD算子的计算功能全部由Executor执行
        val mapRdd: RDD[Int] = listRdd.map(_ * 2)

        mapRdd.collect().foreach(println)

    }

}
