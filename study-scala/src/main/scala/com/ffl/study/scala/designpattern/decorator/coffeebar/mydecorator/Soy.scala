package com.ffl.study.scala.designpattern.decorator.coffeebar.mydecorator

import com.ffl.study.scala.designpattern.decorator.coffeebar.Drink

class Soy(obj: Drink) extends Decorator(obj) {
  setDescription("Soy")
  setPrice(1.5f)
}
