package com.ffl.study.spark.scala.sharedata

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object ShareData {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DistinctOperator")

        // 创建上下文
        val sc = new SparkContext(config)

        val dataRDD: RDD[Int] = sc.makeRDD(1 to 4, 2)

        //val i: Int = dataRDD.reduce(_ + _)

        // 使用累加器共享变量，来累计数据

        // 创建累加对象
        val accumulator: LongAccumulator = sc.longAccumulator

        dataRDD.foreach {
            case i => {
                // 执行累加器功能
                accumulator.add(i)
            }
        }

        // 获取累加器的值
        println(accumulator.value)

        // 释放资源
        sc.stop()
    }

}
