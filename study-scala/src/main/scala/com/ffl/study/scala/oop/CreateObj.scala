package com.ffl.study.scala.oop

/**
  * @author lff
  * @datetime 2020/05/27 09:48
  */
object CreateObj {
    def main(args: Array[String]): Unit = {
        val emp = new Emp
        println(emp)

        val emp2:Person = new Emp
        println(emp2)

        val n = 10
        (0 to n).reverse.foreach(println)
    }

}

class Person {

}

class Emp extends Person {

}
