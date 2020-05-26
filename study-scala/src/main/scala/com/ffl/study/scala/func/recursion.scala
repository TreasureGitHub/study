package com.ffl.study.scala.func

/**
  * @author lff
  * @datetime 2020/05/27 01:04
  *
  *
  */
object recursion {

    def main(args: Array[String]): Unit = {
        println(fn(21))
    }

    def fn(n: Int): Int = {
        if (n == 1) {
            1
        } else if (n == 2) {
            1
        } else {
            fn(n-2) + fn(n-1)
        }

    }
}
