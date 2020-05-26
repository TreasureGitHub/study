package com.ffl.study.scala.flowcontrol

import scala.io.StdIn

/**
  * @author lff
  * @datetime 2020/05/25 23:37
  */
object IfElse {

    def main(args: Array[String]): Unit = {
        println("输入年龄")

        val age = StdIn.readInt()

        val res = if (age > 18)
            println("age > 18")
        else if (age < 12)
            println("age < 12")

        print(res)
    }

}
