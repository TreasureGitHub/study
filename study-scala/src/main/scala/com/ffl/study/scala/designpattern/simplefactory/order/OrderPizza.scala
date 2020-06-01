package com.ffl.study.scala.designpattern.simplefactory.order

import com.ffl.study.scala.designpattern.simplefactory.pizza.Pizza

import scala.io.StdIn
import scala.util.control.Breaks._

/**
  * @author lff
  * @datetime 2020/05/31 23:43
  */
class OrderPizza {

    var orderType: String = _
    var pizza: Pizza = _

    breakable {
        do {
            println("请输入pizza的类型,使用简单工厂模型")
            orderType = StdIn.readLine()
            pizza = SimpleFactory.createPizza(orderType)
            if (pizza == null) {
                break()
            }
            this.pizza.prepare()
            this.pizza.bake()
            this.pizza.cut()
            this.pizza.box()
        } while (true)
    }

}
