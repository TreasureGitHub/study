package com.ffl.study.scala.oop

import scala.beans.BeanProperty

/**
  * @author lff
  * @datetime 2020/05/27 14:11
  */
object ConstructorDemo extends App {

    def main(args: Array[String]): Unit = {
        val person = new Person1("张三", 12)

        println(person)
    }

}

class Person1(inName: String, inAge: Int) {

    var name: String = inName

    var age: Int = inAge

    override def toString: String = "name =" + this.name + " age =" + this.age
}

//@BeanProperty
class Student {

    @BeanProperty
    var id: Long = _

    @BeanProperty
    var name: String = _
}