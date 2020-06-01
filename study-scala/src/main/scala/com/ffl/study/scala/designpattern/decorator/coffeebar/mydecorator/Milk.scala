package com.ffl.study.scala.designpattern.decorator.coffeebar.mydecorator

import com.ffl.study.scala.designpattern.decorator.coffeebar.Drink


class Milk(obj: Drink) extends Decorator(obj) {

    setDescription("Milk")
    setPrice(2.0f)
}
