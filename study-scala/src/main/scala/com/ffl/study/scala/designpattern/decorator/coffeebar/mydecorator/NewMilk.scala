package com.ffl.study.scala.designpattern.decorator.coffeebar.mydecorator

import com.ffl.study.scala.designpattern.decorator.coffeebar.Drink

class NewMilk(obj: Drink) extends Decorator(obj) {

  setDescription("新式Milk")
  setPrice(4.0f)
}