package com.ffl.study.scala.designpattern.abstractfactory.pizzastore.order

import com.ffl.study.scala.designpattern.abstractfactory.pizzastore.pizza.Pizza


trait AbsFactory {

  //一个抽象方法
  def  createPizza(t : String ): Pizza

}
