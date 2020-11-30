package com.ffl.study.spark.scala.sparkcore.share

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/29 18:41
  */
object DefaultAcc {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DefaultAcc")
        val sc = new SparkContext(conf)


        val dataRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 2)

        // 以下方法不行
        var sum = 0
        dataRDD.foreach(i => sum = sum + i) // executor执行，不会回传给 driver
        println("sum = " + sum)     // driver数据，不会改变，输入值为0


        // 以下使用累加器来进行累计
        //1.创建累加器
        val accumulator: LongAccumulator = sc.longAccumulator

        dataRDD.foreach {
            i => accumulator.add(i) // // 2.执行累加功能
        }

        //dataRDD.foreach(i => accumulator = accumulator + i)

        // 3.得到数据
        println("accumulator = " + accumulator.value)

        sc.stop
    }

}
