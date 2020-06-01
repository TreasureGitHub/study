package com.ffl.study.scala.designpattern.decorator.coffeebar.mydecorator

import com.ffl.study.scala.designpattern.decorator.coffeebar.Drink


class Chocolate(obj: Drink) extends Decorator(obj) {

  super.setDescription("Chocolate")
  //一份巧克力3.0f
  super.setPrice(3.0f)

}
