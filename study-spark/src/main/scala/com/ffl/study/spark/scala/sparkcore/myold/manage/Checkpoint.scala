package com.ffl.study.spark.scala.sparkcore.myold.manage

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/20 00:21
  */
object Checkpoint {

    def main(args: Array[String]): Unit = {
        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("checkpoint")

        // 创建上下文
        val sc = new SparkContext(config)

        sc.setCheckpointDir("cp")

        val listRdd: RDD[Int] = sc.makeRDD(List(1,2,3,4))

        val mapRDD: RDD[(Int, Int)] = listRdd.map((_,1))

        val reduceRDD: RDD[(Int, Int)] = mapRDD.reduceByKey(_ + _)

        reduceRDD.checkpoint()

        reduceRDD.foreach(println)

        println(reduceRDD.toDebugString)

        sc.stop

    }

}
