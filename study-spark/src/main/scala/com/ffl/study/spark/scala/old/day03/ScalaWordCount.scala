package com.ffl.study.spark.scala.old.day03

/**
  * @author lff
  * @datetime 2020/01/15 20:09
  */
object ScalaWordCount {

  def main(args: Array[String]): Unit = {
    import org.apache.spark.rdd.RDD
    import org.apache.spark.{SparkConf, SparkContext}
    val conf = new SparkConf().setAppName("ScalaWordCount").setMaster("local")

    // 创建spark 的入口
    val sc = new SparkContext(conf)

    //  指定以后从哪里读取创建RDD (弹性分布式数据集)
    // val lines: RDD[String] = sc.textFile(RESOURCE_PATH + "/input/words.txt")

    val lines: RDD[String] = sc.textFile("hdfs://localhost:9000/wc")

    // 切分压平
    //val words: RDD[String] = lines.flatMap(_.split(" "))
    //
    //// 将单词和一进行组合
    //val wordAndOne: RDD[(String, Int)] = words.map((_,1))
    //
    //println(wordAndOne.toString())
    //
    //val reduced: RDD[(String, Int)] = wordAndOne.reduceByKey(_+_)
    //
    //val sorted: RDD[(String, Int)] = reduced.sortBy(_._2,false)
    //
    //sorted.partitions.length
    //
    //// 保存
    //sorted.saveAsTextFile(RESOURCE_PATH + "/output/text")
    //

    //lines.count()

    // 该函数功能是将对应分区中的数据取出来，并且带上分区编号
    val func = (index:Int,it:Iterator[String]) => {
      it.map(item => s"part:${index}, ele:${item}")
    }

    val rdd2: RDD[String] = lines.mapPartitionsWithIndex(func)

    val arr: Array[String] = rdd2.collect()

    println(arr.mkString)

    //// 释放资源
    sc.stop()

  }

}
