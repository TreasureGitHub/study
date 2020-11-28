package com.ffl.study.spark.scala.sparkcore

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.common.utils.FileUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/28 15:44
  */
object OperatorAction {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OperatorAction")

        val sc = new SparkContext(config)

        val rdd1: RDD[Int] = sc.makeRDD(1 to 10, 2)

        //1.reduce
        //通过 func 函数聚集 RDD 中的所有元素，先聚合分区内数据，再聚合分区间数 据。
        val result: Int = rdd1.reduce(_ + _)

        println(result)
        println("--------------------------reduce-------------------------")


        // 2.collect
        //在驱动程序中，以数组的形式返回数据集的所有元素
        val result1: Array[Int] = rdd1.collect()

        println(result1.mkString(StringConstants.COMMA))
        println("--------------------------collect-------------------------")


        // 3.count
        val cnt: Long = rdd1.count()

        println(cnt)
        println("--------------------------count-------------------------")


        // 4.first
        val result2: Int = rdd1.first()
        println(result2)
        println("--------------------------first-------------------------")


        // 5.take
        //返回一个由 RDD 的前 n 个元素组成的数组
        val result3: Array[Int] = rdd1.take(5)

        println(result3.mkString(StringConstants.COMMA))
        println("--------------------------take-------------------------")

        // 6.takeOrdered
        //返回该 RDD 排序后的前 n 个元素组成的数组
        val rdd2: RDD[Int] = sc.makeRDD(Array(2, 5, 4, 6, 8, 3), 3)
        val rdd3: Array[Int] = rdd2.takeOrdered(3)

        println(rdd3.mkString(StringConstants.COMMA))
        println("--------------------------takeOrdered-------------------------")


        // 7.aggregate
        //aggregate 函数将每个分区里面的元素通过 seqOp 和初始值进行聚合，然后用 combine 函数将每个分区的结果和初始值(zeroValue)进行
        //combine 操作。这个函数最终返回 的类型不需要和 RDD 中元素类型一致
        val result4: Int = rdd1.aggregate(0)(_ + _, _ + _)

        println(result4)
        println("--------------------------aggregate-------------------------")

        //8 fold(num)
        //折叠操作，aggregate 的简化操作，seqop 和 combop 一样
        val result5: Int = rdd1.fold(0)(_ + _)
        println(result5)
        println("--------------------------result5-------------------------")


        //9 saveAsTextFile(path)
        val basePath = PathConstants.SPARK_RES + "/action/"
        FileUtils.deleteDir(basePath + "text");
        rdd1.saveAsTextFile(basePath + "text")


        //10 saveAsSequenceFile(path)
        FileUtils.deleteDir(basePath + "seq");
        rdd1.saveAsObjectFile(basePath + "seq")


        //11 saveAsObjectFile(path)
        FileUtils.deleteDir(basePath + "obj");
        rdd1.saveAsObjectFile(basePath + "obj")

        //12 countByKey()
        val rdd4 = sc.makeRDD(List((1, 3), (1, 2), (1, 4), (2, 3), (3, 6), (3, 8)), 3)
        val rdd5: collection.Map[(Int, Int), Long] = rdd4.countByValue()

        println(rdd5)
        println("--------------------------countByKey-------------------------")

        //13 foreach(func)
        val rdd6: RDD[Int] = sc.makeRDD(1 to 5, 2)
        rdd6.foreach(println)
        rdd6.foreach(_ * 2)

        rdd6.collect().foreach(_ * 2)

        sc.stop()
    }

}
