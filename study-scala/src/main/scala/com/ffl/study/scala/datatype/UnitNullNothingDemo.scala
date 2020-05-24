package com.ffl.study.scala.datatype

/**
  * @author lff
  * @datetime 2020/05/20 23:53
  */
object UnitNullNothingDemo {

    def main(args: Array[String]): Unit = {

        val hello: Unit = sayHello

        println("res = " + hello)

        val dog:Dog = null

        // 错误
        //val char:Char = null

        //println("char " + char)

        //print(math.sqrt(2))
    }


    // Unit等价于java中的void，只有一个实例 ()
    def sayHello:Unit = {

    }


}

class Dog {


}