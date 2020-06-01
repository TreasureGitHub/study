package com.ffl.study.scala.designpattern.simplefactory.pizza

/**
  * @author lff
  * @datetime 2020/05/31 23:40
  */
class PepperPizza extends Pizza {

    override def prepare(): Unit = {
        this.name = "胡椒pizza"
        println(this.name + "preparing")
    }
}
