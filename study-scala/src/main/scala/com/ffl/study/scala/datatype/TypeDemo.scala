package com.ffl.study.scala.datatype

/**
  * @author lff
  * @datetime 2020/05/20 23:23
  */
object TypeDemo {

    def main(args: Array[String]): Unit = {
        //sayHello
        println(Long.MaxValue)
        println(Long.MinValue)
    }

    def sayHello: Nothing = {
        throw new Exception("排除异常")
    }

}
