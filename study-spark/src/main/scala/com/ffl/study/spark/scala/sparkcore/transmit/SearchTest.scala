package com.ffl.study.spark.scala.sparkcore.transmit

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/28 22:14
  */
object SearchTest {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("SearchTest").setMaster("local[*]")

        val sc = new SparkContext(conf)

        val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "hive", "atguigu"))

        val search = new Search("h")

        // 传递方法
        // 在方法中调用的方法isMatch是定义在Search 类中，实际上是调用this.isMatch,程序在运行过程中需要将Search对象序列化后传递到Executor
        search.getMatch1(rdd).collect().foreach(println)

        // 传递属性

        search.getMatch2(rdd).collect().foreach(println)

        sc.stop()
    }

}

class Search(query: String) extends Serializable {

    /**
      * 是否匹配
      *
      * @param str
      * @return
      */
    def isMatch(str: String): Boolean = {
        str.contains(query)
    }


    def getMatch1(rdd: RDD[String]): RDD[String] = {
        rdd.filter(isMatch)
    }

    def getMatch2(rdd: RDD[String]): RDD[String] = {
        val s = query
        rdd.filter(x => x.contains(s))
    }

}
