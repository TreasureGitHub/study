package com.ffl.study.scala.designpattern.abstractfactory.pizzastore.pizza

class BJPepperPizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "北京胡椒Pizza"
    println(this.name + " preparing..")
  }
}
