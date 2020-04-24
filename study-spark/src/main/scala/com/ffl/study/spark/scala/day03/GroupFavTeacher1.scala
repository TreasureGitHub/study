package com.ffl.study.spark.scala.day03

import java.net.URL

import com.ffl.study.spark.scala.constant.PathConstants.RESOURCE_PATH
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/03/08 11:41
  */
object GroupFavTeacher1 {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("GroupFavTearch1").setMaster("local[2]")
        val sc = new SparkContext(conf)

        val topN = 10

        val line: RDD[String] = sc.textFile(RESOURCE_PATH + "/input/access.log")

        val subjectAndTearch: RDD[((String, String), Int)] = line.map(line => {
            val index: Int = line.lastIndexOf("/")
            val teacher = line.substring(index + 1)
            val httpHost = line.substring(0, index)
            val subject = new URL(httpHost).getHost.split("[.]")(0)

            ((subject, teacher), 1)
        })

        // 聚合，将学科和老师联合当成key
        val reduced: RDD[((String, String), Int)] = subjectAndTearch.reduceByKey(_ + _)

        // 分组排序，按学科进行分组
        // [学科，该学科对应的老师的数据]
        val grouped: RDD[(String, Iterable[((String, String), Int)])] = reduced.groupBy(
            (t: ((String, String), Int)) => t._1._1, 4
        )

        // 经过分组后，一个分区内可能有多个学科的数据，一个学科就是一个迭代器
        // 将每一个组拿来进行操作
        // 为什么可以调用scala的sortBy方法呢？因为一个学科的数据已经在一台机器上的一个scala集合里面了
        val sorted: RDD[(String, List[((String, String), Int)])] = grouped.mapValues(_.toList.sortBy(_._2).reverse.take(topN))

        val result: Array[(String, List[((String, String), Int)])] = sorted.collect

        println(result.toBuffer)

        sc.stop()
    }

}
