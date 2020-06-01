package com.ffl.study.scala.designpattern.decorator.coffeebar.mycoffee

import com.ffl.study.scala.designpattern.decorator.coffeebar.Drink


//在Drink 和  单品咖啡，我做了一个缓冲层
//这里是为了扩展，针对当前项目可以不要
class Coffee extends Drink{
  override def cost(): Float = {
    super.getPrice()
  }
}
