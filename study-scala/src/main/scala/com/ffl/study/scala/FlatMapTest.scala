package com.ffl.study.scala

/**
  * @author lff
  * @datetime 2020/11/21 17:10
  */
object FlatMapTest {


    def main(args: Array[String]): Unit = {
        val list = List("Alice", "Bob", "Nick")

        val chars: List[Char] = list.flatMap(_.toUpperCase)


        chars.foreach(println)

    }

}
