package com.ffl.study.spark.scala.sparkcore.share

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * @author lff
  * @datetime 2020/11/29 20:42
  */
object BroadCast {

    def main(args: Array[String]): Unit = {

        val conf: SparkConf = new SparkConf().setMaster("local").setAppName("BroadCast")
        val sc = new SparkContext(conf)

        val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1),("b", 2),("c", 3)))

        val map: mutable.Map[String, Int] = mutable.Map(("a", 4),("b", 5),("c", 6))

        // 封装广播变量
        val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)

        rdd1.map {
            case (w, c) => {
                // 方法广播变量
                val l: Int = bc.value.getOrElse(w, 0)
                (w, (c, l))
            }
        }.collect().foreach(println)


        sc.stop()
    }

}
