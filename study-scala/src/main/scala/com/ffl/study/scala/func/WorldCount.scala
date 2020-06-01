package com.ffl.study.scala.func

/**
  * @author lff
  * @datetime 2020/05/30 21:18
  *
  */
object WorldCount {

    def main(args: Array[String]): Unit = {
        val lines = List("hello world world", "hello scala", "hello spark scala", "kafka")

        lines
          .flatMap(_.split(" "))
          .map((_, 1))
          .groupBy(_._1)
          .map(x => (x._1, x._2.size))
          .toList
          .sortBy(_._2)
          .foreach(println)
    }
}