package com.ffl.study.scala

/**
  * @author lff
  * @datetime 2020/04/08 13:54
  *
  * 1.object TestScala对应的是一个 TestScala$的一个静态对象 MODULE$
  * 2.在程序中是一个单例
  */
object HelloWorld {

    def main(args: Array[String]): Unit = {
        println("hello scala")


        val num:Int = 10

        println(num.toDouble)
    }



}
