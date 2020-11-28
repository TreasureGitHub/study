package com.ffl.study.spark.scala.sparkcore

import com.ffl.study.common.constants.StringConstants
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/27 09:04
  */
object Operator2Value {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator2Value")

        val sc = new SparkContext(config)


        val listRDD1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 3, 1), 3)
        val listRDD2: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6, 4, 3), 3)

        println("listRDD1  partition:" + listRDD1.partitions.length)
        println("listRDD2  partition:" + listRDD2.partitions.length)

        // 1.union 并集，可以看到 取并集后数据分区大小 为  输入分区的综合
        // 此处并没有去重，可以结合distinct使用
        val listRDD3: RDD[Int] = listRDD1.union(listRDD2)

        println("listRDD3  partition:" + listRDD3.partitions.length)
        listRDD3.glom().collect().foreach(x => println(x.mkString(StringConstants.COMMA)))

        println("------------------------union------------------------")
        listRDD3.distinct().glom.collect.foreach(x => println(x.mkString(StringConstants.COMMA)))

        println("------------------------disintct------------------------")

        //2.intersection 交集,结果去重 分区数取两者较大分区
        val listRDD4: RDD[Int] = listRDD2.intersection(listRDD1)
        listRDD4.glom().collect().foreach(x => println(x.mkString(StringConstants.COMMA)))

        println("------------------------intersection------------------------")

        // 3.subtract A 去掉 B中有的数据，分区数和A相等
        val listRDD5: RDD[Int] = listRDD1.subtract(listRDD2)
        listRDD5.glom().collect().foreach(x => println(x.mkString(StringConstants.COMMA)))

        println("------------------------subtract------------------------")


        // 4.zip
        //将两个 RDD 组合成 Key/Value 形式的 RDD,这里默认两个 RDD 的 partition 数量 以及元素数量都相同，否则会抛出异常
        val listRDD6: RDD[(Int, Int)] = listRDD1.zip(listRDD2)
        listRDD6.collect().foreach(println)
        println(listRDD6.partitions.size)
        println("------------------------zip------------------------")

        sc.stop
    }

}
