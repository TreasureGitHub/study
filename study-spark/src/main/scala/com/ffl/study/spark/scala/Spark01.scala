package com.ffl.study.spark.scala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 12:56
  */
object Spark01 {

    def main(args: Array[String]): Unit = {

        //创建RDD
        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")

        val sc = new SparkContext(config)

        // 1.从内存中创建,底层实现就是 parallelize
        //val listRdd: RDD[Int] = sc.makeRDD(List(1,2,3,4))
        // 自定义分区
        //val listRdd: RDD[Int] = sc.makeRDD(List(1,2,3,4),2)

        // 2.从内存中创建
        val arrayRdd: RDD[Int] = sc.parallelize(Array(1, 2, 3, 4))

        // 3.从外部存储中创建
        // 默认情况下可以直接读取项目路径，也可以读取其它路径:hdfs
        // 默认从文件中读取的是字符串类型
        // 读取文件时，传递的分区参数为最小分区数，但是不一定是这个分区数，取决于hadoop读取文件时的分片规则
        val basePath: String = getClass.getResource(".").getPath

        val inputPath: String = getClass.getResource("/input/word.txt").getPath

        val fileRdd: RDD[String] = sc.textFile(inputPath, 3)

        //arrayRdd.collect().foreach(println)

        // 将RDD的数据保存至文件中
        fileRdd.saveAsTextFile("output")
    }

}
