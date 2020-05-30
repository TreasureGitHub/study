package com.ffl.study.scala.implicittest

/**
  * @author lff
  * @datetime 2020/05/30 11:21
  */
object Double2Int {

    def main(args: Array[String]): Unit = {

        implicit def f1(d:Double):Int = {
            d.toInt
        }

        val num:Int = 3.5

        println(num)
    }

}
