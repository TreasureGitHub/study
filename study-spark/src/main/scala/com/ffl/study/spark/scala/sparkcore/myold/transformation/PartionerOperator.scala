package com.ffl.study.spark.scala.sparkcore.myold.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/05/19 19:44
  */
object PartionerOperator {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("PartionerOperator")

        val sc = new SparkContext(config)

        val listRDD: RDD[(String, Int)] = sc.makeRDD(List(("a",1),("b",2),("c",3)))

        val partRDD: RDD[(String, Int)] = listRDD.partitionBy(new MyPartitioner(3))

        partRDD.saveAsTextFile("output");

    }

}

class MyPartitioner(partitions:Int) extends Partitioner {

    override def numPartitions: Int = {
        partitions
    }

    override def getPartition(key: Any): Int = {
        1
    }
}
