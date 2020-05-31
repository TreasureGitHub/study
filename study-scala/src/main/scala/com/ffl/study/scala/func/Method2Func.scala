package com.ffl.study.scala.func

/**
  * @author lff
  * @datetime 2020/05/26 23:57
  *
  * 方法转函数
  */
object Method2Func {

    def main(args: Array[String]): Unit = {
        val i: Int = sum(1,2)
        println(i)

        // 方法转函数
        val f1 = sum _

        println(f1)

        // 函数求两个数的和
        val f2 = (n1:Int,n2:Int) => {
            n1 + n2
        }

        println(f2)

    }

    // 方法
    def sum(n1: Int, n2: Int) = {
        n1 + n2
    }
}
