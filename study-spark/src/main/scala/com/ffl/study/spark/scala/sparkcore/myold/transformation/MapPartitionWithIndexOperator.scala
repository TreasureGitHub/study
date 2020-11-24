package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object MapPartitionWithIndexOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark02_Oper3")

        // 创建上下文
        val sc = new SparkContext(config)

        // map 算子
        val listRDD: RDD[Int] = sc.makeRDD(1 to 10,2)

        // mapPartitions可以对一个RDD中所有的分区进行遍历
        // mapPartitions效率优于map算子，减少了发送到执行器执行交换次数

        val tumpleRDD: RDD[(Int, String)] = listRDD.mapPartitionsWithIndex {
            case (num, datas) => {
                datas.map((_, " 分区号:" + num))
            }
        }

        tumpleRDD.collect().foreach(println)

    }

}
