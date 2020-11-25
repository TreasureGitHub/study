package com.ffl.study.spark.scala

/**
  * @author lff
  * @datetime 2020/11/25 21:14
  */
object Test {

    def main(args: Array[String]): Unit = {
        val strings = List("hello","world")

        val tuples: List[(String, Int)] = strings.map((_,1))


        println(tuples)
    }

}
