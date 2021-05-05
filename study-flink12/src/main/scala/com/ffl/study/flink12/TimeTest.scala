package com.ffl.study.flink12


/**
  * @author lff
  * @datetime 2021/05/04 16:38
  */
object TimeTest {

    private val CLASS_NAME: String = TimeTest.getClass.getName

    def main(args: Array[String]): Unit = {
        println(Long.MinValue)
    }


    case class Order(user: String, product: String, amount: String)

}

