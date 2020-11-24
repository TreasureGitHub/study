package com.ffl.study.spark.scala.sparkcore

import com.ffl.study.common.constants.PathConstants
import com.ffl.study.common.utils.FileUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/17 12:56
  */
object RDDCreate {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDCreate")

        val sc = new SparkContext(config)

        // 1.1从内存中创建  makeRDD
        // 默认是和内核数一样的分区
        val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        // 自定义分区，定义分区数为2
        //val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

        listRDD.collect()

        // 输出路径
        val outPath = PathConstants.SPARK_RES + "/output";
        FileUtils.deleteDir(outPath)
        listRDD.saveAsTextFile(outPath)

        // 1.2从内存中创建  parallelize
        val listRDD1: RDD[Int] = sc.parallelize(Array(1,2,3,4))

        //val listRDD1: RDD[Int] = sc.parallelize(Array(1,2,3,4),2)

        listRDD1.collect().foreach(println)

        // 2.从文件读取，默认为2
        val fileRDD: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/input")

        // 指定分区数
        // 读取文件时，传递的分区参数为最小分区数，但是不一定是分区数，取决于hadoop读取文件时的分区规则
        //val fileRDD: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/input",3)

        fileRDD.collect().foreach(println)

    }

}
