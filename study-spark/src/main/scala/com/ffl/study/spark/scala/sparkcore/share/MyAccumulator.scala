package com.ffl.study.spark.scala.sparkcore.share

import java.util

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 自定义累加器
  *
  * @author lff
  * @datetime 2020/11/29 18:59
  */
object MyAccumulator {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DefaultAcc")
        val sc = new SparkContext(conf)

        // 创建累加器
        val wordAccumulator = new WordAccumulator

        // 注册累加器
        sc.register(wordAccumulator)

        val dataRDD: RDD[String] = sc.makeRDD(List("hadoop", "hbase", "scala", "hive"), 2)

        dataRDD.foreach {
            word => wordAccumulator.add(word)
        }

        println(wordAccumulator.value)

    }
}

class WordAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {

    private val list = new util.ArrayList[String];

    // 是否为初始化状态
    override def isZero: Boolean = list.isEmpty

    // 复制累加器对象
    override def copy(): AccumulatorV2[String, util.ArrayList[String]] = new WordAccumulator

    //
    override def reset(): Unit = list.clear()

    // 增加
    override def add(v: String): Unit = {
        if (v.contains("h")) {
            list.add(v)
        }
    }

    override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = list.addAll(other.value)

    override def value: util.ArrayList[String] = list
}