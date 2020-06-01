package com.ffl.study.scala.designpattern.singleton

/**
  * @author lff
  * @datetime 2020/06/01 22:40
  */
object TestSingle {

    def main(args: Array[String]): Unit = {
        val ton: SingleTon = SingleTon.getInstance()
        val ton1: SingleTon = SingleTon.getInstance()

        println(ton == ton1)
    }

}

// 构造方法私有化
class SingleTon private() {

}

// 懒汉式
object SingleTon {

    private var singleTon: SingleTon = _


    def getInstance(): SingleTon = {
        if (singleTon == null) {
            singleTon = new SingleTon
        }

        singleTon
    }

}
