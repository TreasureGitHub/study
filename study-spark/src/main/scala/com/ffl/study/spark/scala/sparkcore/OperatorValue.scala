package com.ffl.study.spark.scala.sparkcore

import com.ffl.study.common.constants.StringConstants
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/19 12:45
  */
object OperatorValue {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setAppName("OperatorValue").setMaster("local[*]")

        val sc = new SparkContext(config)

        val listRDD: RDD[Int] = sc.parallelize(1 to 10)

        // 1.map
        //将处理的数据逐条进行映射转换，这里的转换可以是类型的转换，也可以是值的转换
        listRDD.map(_ * 2).collect().foreach(println)

        // mapPartitions 可以对一个RDD中所有分区进行遍历
        // mapPartitions 效率优于map算子，减少了发送到执行器执行交互数
        // mapPartitions 可能会出现内存溢出(oom)
        println("------------------------map------------------------")

        // 2.mapPartitions
        //将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处 理，哪怕是过滤数据。
        listRDD.mapPartitions(datas => {
            datas.filter(_ > 2).map(_ * 2)
        }).collect().foreach(println)

        println("------------------------mapPartitions------------------------")

        // 3.mapPartitionsWithIndex
        //将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处 理，哪怕是过滤数据，在处理时同时可以获取当前分区索引。
        val tupleRDD: RDD[(Int, String)] = listRDD.mapPartitionsWithIndex {
            case (num, data) => {
                data.map((_, "分区号：" + num))
            }
        }

        tupleRDD.foreach(println)
        println("------------------------mapPartitionsWithIndex------------------------")

        // 4.flatMap
        //将处理的数据进行扁平化后再进行映射处理，所以算子也称之为扁平映射
        val dataRDD: RDD[List[Int]] = sc.parallelize(Array(List(1, 2), List(3, 4)))
        val resultRDD: RDD[Int] = dataRDD.flatMap(datas => datas)
        resultRDD.collect().foreach(println)

        println("------------------------flatMap------------------------")

        // 5.glom
        //将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样 的操作称之为 shuffle。
        //极限情况下，数据可能被分在同一个分区中 一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
        val dataRDD1: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4, 5, 6, 7, 8), 3)
        val resultRDD1: RDD[Array[Int]] = dataRDD1.glom()
        resultRDD1.collect().foreach(arr => println(arr.mkString(",")))

        println("------------------------glom------------------------")

        // 6.groupBy,分组后的数据形成了 对偶元组(K,V)
        //将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样 的操作称之为 shuffle
        //极限情况下，数据可能被分在同一个分区中
        val dataRDD2: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        val resultRDD2: RDD[(Int, Iterable[Int])] = dataRDD2.groupBy(_ % 2)
        resultRDD2.collect().foreach(println)

        // groupBy,分组后的数据形成了 对偶元组(K,V)
        //val dataRDD3: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        println("------------------------groupBy------------------------")
        //7.filter
        //将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃。 当数据进行筛选过滤后，分区不变，
        //但是分区内的数据可能不均衡，生产环境下，可能会出 现数据倾斜。
        val resultRDD3: RDD[Int] = dataRDD2.filter(_ % 2 == 0)
        resultRDD3.collect().foreach(println)

        println("------------------------filter------------------------")
        // 8.sample
        // false 是否放回，
        // 从指定集合中进行抽样处理，根据不同的算法进行抽样
        val dataRDD4: RDD[Int] = sc.makeRDD(1 to 10)
        val resultRDD4: RDD[Int] = dataRDD4.sample(false, 0.6, 1)
        resultRDD4.collect().foreach(println)

        // 从指定集合中进行抽样处理，根据不同的算法进行抽样
        val dataRDD5: RDD[Int] = sc.makeRDD(List(1, 2, 1, 5, 2, 9, 6, 1))

        println("------------------------sample------------------------")
        // 9.distinct
        // 将数据集中重复的数据去重
        // 传入参数为分区数
        val resultRDD5: RDD[Int] = dataRDD5.distinct(2)
        resultRDD5.collect().foreach(println)

        println("------------------------distinct------------------------")


        // 10 coalesce
        // 根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率
        // 当 spark 程序中，存在过多的小任务的时候，可以通过 coalesce 方法，收缩合并分区，减少 分区的个数，减小任务调度成本
        val dataRDD6: RDD[Int] = sc.makeRDD(1 to 16, 4)

        println("缩减前分区：" + dataRDD6.partitions.size)
        dataRDD6.glom().collect.foreach(x => println(x.mkString(",") + StringConstants.TAB))
        // 第二个参数为是否进行shuffle，不传默认为false
        val dataRDD7: RDD[Int] = dataRDD6.coalesce(2)

        println("缩减后分区：" + dataRDD7.partitions.size)
        dataRDD7.glom().collect.foreach(x => println(x.mkString(",") + StringConstants.TAB))
        println("------------------------coalesce------------------------")

        // 11 repartition
        // 该操作内部其实执行的是 coalesce 操作，参数 shuffle 的默认值为 true。无论是将分区数多的 RDD 转换为分区数少的 RDD，还是将分区数少的 RDD 转换为分区数多的 RDD
        // repartition 操作都可以完成，因为无论如何都会经 shuffle 过程
        val dataRDD8: RDD[Int] = sc.makeRDD(1 to 16, 4)

        val dataRDD9: RDD[Int] = dataRDD8.repartition(2)
        println(dataRDD9.partitions.size)
        println("------------------------repartition------------------------")

        // 12.排序,降序
        //该操作用于排序数据。在排序之前，可以将数据通过 f 函数进行处理，之后按照 f 函数处理 的结果进行排序，默认为升序排列。
        // 排序后新产生的 RDD 的分区数与原 RDD 的分区数一 致。中间存在 shuffle 的过程
        dataRDD7.sortBy(x => x, false).collect().foreach(println)
        println("------------------------sortBy------------------------")

        sc.stop()
    }

}
