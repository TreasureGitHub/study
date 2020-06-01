package com.ffl.study.scala.designpattern.factorymethod.order

import com.ffl.study.scala.designpattern.factorymethod.pizza.Pizza

import scala.io.StdIn
import scala.util.control.Breaks._

/**
  * @author lff
  * @datetime 2020/05/31 23:43
  */
abstract class OrderPizza {

    var orderType: String = _
    var pizza: com.ffl.study.scala.designpattern.factorymethod.pizza.Pizza = _

    breakable {
        do {
            println("请输入pizza的类型,使用工厂方法模型 ~ ~ ~")
            orderType = StdIn.readLine()
            pizza = createPizza(orderType)
            if (pizza == null) {
                break()
            }
            this.pizza.prepare()
            this.pizza.bake()
            this.pizza.cut()
            this.pizza.box()
        } while (true)
    }

    // 抽象的方法，createPizza(orderType),让各个子类去实现
    def createPizza(name: String): Pizza

}
