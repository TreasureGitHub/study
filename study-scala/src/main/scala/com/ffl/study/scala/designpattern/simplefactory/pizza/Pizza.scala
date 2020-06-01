package com.ffl.study.scala.designpattern.simplefactory.pizza

/**
  * @author lff
  * @datetime 2020/05/31 23:39
  */
abstract class Pizza {

    var name: String = _

    def prepare()

    def cut(): Unit = {
        println(this.name + "  cutting ..")
    }

    def bake(): Unit = {
        println(this.name + "  baking ..")
    }

    def box(): Unit = {
        println(this.name + "  boxing ..")
    }

}
