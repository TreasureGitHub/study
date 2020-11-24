package com.ffl.study.spark.scala.sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/19 12:45
  */
object Operator1 {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setAppName("Operator1").setMaster("local[*]")

        val sc = new SparkContext(config)

        val listRDD: RDD[Int] = sc.parallelize(1 to 10)

        // map
        listRDD.map(_ * 2).collect().foreach(println)

        // mapPartitions 可以对一个RDD中所有分区进行遍历
        // mapPartitions 效率优于map算子，减少了发送到执行器执行交互数
        // mapPartitions 可能会出现内存溢出(oom)

        // mapPartitions
        listRDD.mapPartitions(datas => {
            datas.map(_ * 2)
        }).collect().foreach(println)

        // mapPartitionsWithIndex
        val tupleRDD: RDD[(Int, String)] = listRDD.mapPartitionsWithIndex {
            case (num, datas) => {
                datas.map((_, "分区号：" + num))
            }
        }

        tupleRDD.foreach(println)
        val dataRDD: RDD[List[Int]] = sc.parallelize(Array(List(1, 2), List(3, 4)))

        // flatMap
        val resultRDD: RDD[Int] = dataRDD.flatMap(datas => datas)
        resultRDD.collect().foreach(println)

        val dataRDD1: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4, 5, 6, 7, 8), 3)


        //glom
        val resultRDD1: RDD[Array[Int]] = dataRDD1.glom()
        resultRDD1.collect().foreach(arr => println(arr.mkString(",")))


        // groupBy,分组后的数据形成了 对偶元组(K,V)
        val dataRDD2: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        val resultRDD2: RDD[(Int, Iterable[Int])] = dataRDD2.groupBy(_ % 2)
        resultRDD2.collect().foreach(println)


        // groupBy,分组后的数据形成了 对偶元组(K,V)
        val dataRDD3: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        val resultRDD3: RDD[Int] = dataRDD2.filter(_ % 2 == 0)
        resultRDD3.collect().foreach(println)

        // 从指定集合中进行抽样处理，根据不同的算法进行抽样
        val dataRDD4: RDD[Int] = sc.makeRDD(1 to 10)

        // false 是否放回，
        val resultRDD4: RDD[Int] = dataRDD4.sample(false, 0.6, 1)
        resultRDD4.collect().foreach(println)

        // 从指定集合中进行抽样处理，根据不同的算法进行抽样
        val dataRDD5: RDD[Int] = sc.makeRDD(List(1,2,1,5,2,9,6,1))

        // false 是否放回，
        val resultRDD5: RDD[Int] = dataRDD5.distinct(2)
        resultRDD5.collect().foreach(println)


        // 缩减分区数:合并分区；没有打乱重组，没有shuffle
        val dataRDD6: RDD[Int] = sc.makeRDD(1 to 16,4)

        println("缩减前分区：" + dataRDD6.partitions.size)

        val dataRDD7: RDD[Int] = dataRDD6.coalesce(3)

        println("缩减后分区：" + dataRDD7.partitions.size)


        // 排序,降序
        dataRDD7.sortBy(x => x,false).collect().foreach(println)

        sc.stop()
    }

}
