package com.ffl.study.spark.scala.sparkcore.inout

import com.ffl.study.common.constants.PathConstants
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.parsing.json.JSON

/**
  * @author lff
  * @datetime 2020/11/29 17:35
  */
object JsonRead {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("JsonRead")
        val sc = new SparkContext(config)
        val json: RDD[String] = sc.textFile(PathConstants.SPARK_RES + "/json_input/user.json")

        val result: RDD[Option[Any]] = json.map(JSON.parseFull)

        result.foreach(println)

        sc.stop
    }

}
