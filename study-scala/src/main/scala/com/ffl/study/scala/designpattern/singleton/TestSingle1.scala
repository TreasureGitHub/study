package com.ffl.study.scala.designpattern.singleton

/**
  * @author lff
  * @datetime 2020/06/01 22:40
  */
object TestSingle1 {

    def main(args: Array[String]): Unit = {
        val ton: SingleTon1 = SingleTon1.getInstance()
        val ton1: SingleTon1 = SingleTon1.getInstance()

        println(ton == ton1)
    }

}

// 构造方法私有化
class SingleTon1 private() {

}

// 懒汉式
object SingleTon1 {

    private val singleTon: SingleTon1 = new SingleTon1


    def getInstance(): SingleTon1 = {
        singleTon
    }

}
