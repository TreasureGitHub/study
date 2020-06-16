package com.ffl.study.spark.scala

import com.ffl.study.common.constants.PathConstants
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @author lff
  * @datetime 2020/05/24 23:16
  */
@deprecated
object JsonParse {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark02_Oper2")

        // 创建上下文
        val sc = new SparkContext(config)
        val json: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/user/user.json")


    }

}
