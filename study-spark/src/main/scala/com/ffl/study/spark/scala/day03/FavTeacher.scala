package com.ffl.study.spark.scala.day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import com.ffl.study.spark.scala.constant.PathConstants.RESOURCE_PATH

/**
  * @author lff
  * @datetime 2020/03/01 18:44
  */
object FavTeacher {

    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("FavTeacher").setMaster("local[4]")
        val sc = new SparkContext(conf)

        // 指定以后从哪里读取数据
        val line:RDD[String] = sc.textFile(RESOURCE_PATH + "/input/access.log")


        val teacherAndOne: RDD[(String, Int)] = line.map(line => {
            val index: Int = line.lastIndexOf("/")
            val teacher = line.substring(index + 1)
            //val httpHost = line.substring(0,index)
            //val subject = new URL(httpHost).getHost.split("[.]")(0)
            (teacher, 1)
        })

        // 聚合
        val reduced: RDD[(String, Int)] = teacherAndOne.reduceByKey(_ + _)

        // 排序
        val sorted: RDD[(String, Int)] = reduced.sortBy(_._2,false)

        val result: Array[(String, Int)] = sorted.collect

        println(result.toBuffer)

        sc.stop()
    }

}
