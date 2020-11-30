package com.ffl.study.spark.scala.sparkcore.cache

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/29 09:32
  */
object CacheTest {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("CacheTest").setMaster("local[*]")
        val sc = new SparkContext(conf)

        val dataRDD: RDD[String] = sc.makeRDD(Array("hello","scala","spark"))

        val noCache: RDD[(String, Long)] = dataRDD.map((_,System.currentTimeMillis()))

        // 没有缓存，三次结果不一样
        println(noCache.collect().mkString)
        println(noCache.collect().mkString)
        println(noCache.collect().mkString)

        val cache: RDD[(String, Long)] = dataRDD.map((_,System.currentTimeMillis())).cache()

        println("------------------------noCache-----------------------")

        println(cache.collect().mkString)
        println(cache.collect().mkString)
        println(cache.collect().mkString)

        println("------------------------cache-----------------------")
        cache.unpersist()

        println(cache.collect().mkString)

        // 取消 缓存 unpersist
        println("------------------------unpersist-----------------------")

        // 用 persist 持久化
        val persist: RDD[(String, Long)] = dataRDD.map((_,System.currentTimeMillis())).persist(StorageLevel.MEMORY_AND_DISK)

        println(persist.collect().mkString)

        sc.stop()

    }
}
