package com.ffl.study.spark.scala.sparkcore.myold.old.day03

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/03/08 11:41
  */
object GroupFavTeacher2 {

    var RESOURCE_PATH = "/Users/feifeiliu/Documents/work/java/study/study-spark/src/main/resources"

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("GroupFavTearch2").setMaster("local[2]")
        val sc = new SparkContext(conf)

        val topN = 10

        val subjects = Array("bigdata", "java", "react", "flink", "spark")

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

        for (sb <- subjects) {
            // scala的结合排序是在内存中进行的，但是内存有可能不够用
            // 可以调用RDD 的sortby方法，内存 + 磁盘进行排序
            val filtered: RDD[((String, String), Int)] = reduced.filter(_._1._1 == sb)

            val favTeacher: Array[((String, String), Int)] = filtered.sortBy(_._2, false).take(topN)

            // 打印
            println(favTeacher.toBuffer)
        }

        sc.stop()
    }

}
