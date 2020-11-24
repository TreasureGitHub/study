package com.ffl.study.spark.scala.sparkcore.myold.sharedata

import java.util

import com.google.common.collect.Lists
import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  *
  *           自定义累加器
  */
object MyAccumulator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DistinctOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        val dataRDD: RDD[String] = sc.makeRDD(List("hadoop","hive","hbase","zookeeper"), 2)

        // 创建累加器
        val accumulator = new WordAccumulator
        // 注册累加器
        sc.register(accumulator)

        dataRDD.foreach{
            case word => {
                // 执行累加器累加功能
                accumulator.add(word)
            }
        }

        // 获取累加器的值
        println(accumulator.value)

        // 释放资源
        sc.stop()
    }

}

// 申明累加器
class WordAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {

    private val list: util.ArrayList[String] = Lists.newArrayList[String]()

    // 当前累加器是否为初始化状态
    override def isZero: Boolean = list.isEmpty

    // 复制累加器对象
    override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
        new WordAccumulator
    }

    override def reset(): Unit = {
        list.clear()
    }

    // 向累加器中增加数据
    override def add(v: String): Unit = {
        if (v.contains("h")) {
            list.add(v)
        }
    }

    // 合并累加器
    override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = {
        list.addAll(other.value);
    }

    override def value: util.ArrayList[String] = list
}