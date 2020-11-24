package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 17:04
  */
object GroupByOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark02_Oper3")

        // 创建上下文
        val sc = new SparkContext(config)

        // map 算子
        val listRDD: RDD[Int] = sc.makeRDD(1 to 10).filter(_ > 3)

        // 分组后的数据形成了对偶元组(K-V)，k表示分组的key，v表示分组的数据集合
        val groupByRDD: RDD[(Int, Iterable[Int])] = listRDD.groupBy(i => i % 2)

        groupByRDD.collect().foreach(println)

    }

}
