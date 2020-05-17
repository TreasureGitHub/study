package com.ffl.study.spark.scala.old.day03

import java.net.URL

/**
  * @author lff
  * @datetime 2020/03/08 09:50
  */
object Test {

    def main(args: Array[String]): Unit = {
        val lines:String = "http://bigdata.edu360.cn/laozhao"

        val splits: Array[String] = lines.split("/")

        //val subject = splits(2).split("[.]")(0);
        //val teacher = splits(3);

        val index: Int = lines.lastIndexOf("/")

        val teacher = lines.substring(index + 1)

        val httpHost = lines.substring(0,index)

        val hostName = new URL(httpHost).getHost.split("[.]")(0)

        //println(subject)
        println(teacher)
        println(hostName)
    }

}
