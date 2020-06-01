package com.ffl.study.scala.designpattern.factorymethod.pizza

/**
  * @author lff
  * @datetime 2020/05/31 23:40
  */
class BJCheesePizza extends Pizza {

    override def prepare(): Unit = {
        this.name = "背景奶酪pizza"
        println(this.name + "preparing..")
    }
}
