package com.ffl.study.spark.scala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object Serializable {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DistinctOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        val rdd: RDD[String] = sc.parallelize(Array("hadoop", "hive", "hbase", "flink"))

        val search = new Search("h")

        val match1: RDD[String] = search.getMatch1(rdd)

        match1.collect().foreach(println)
    }

}

class Search(query: String) extends Serializable {

    def isMatch(s: String): Boolean = {
        s.contains(query)
    }

    def getMatch(rdd: RDD[String]): RDD[String] = {
        rdd.filter(isMatch)
    }

    def getMatch1(rdd: RDD[String]): RDD[String] = {
        rdd.filter(x => x.contains(query))
    }
}