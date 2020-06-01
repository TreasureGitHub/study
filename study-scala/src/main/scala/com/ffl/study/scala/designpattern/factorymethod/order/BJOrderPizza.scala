package com.ffl.study.scala.designpattern.factorymethod.order

import com.ffl.study.scala.designpattern.factorymethod.pizza.{BJCheesePizza, Pizza}

/**
  * @author lff
  * @datetime 2020/06/01 00:14
  */
class BJOrderPizza extends OrderPizza {

    override def createPizza(name: String): Pizza = {
        var pizza: Pizza = null
        if (name.equals("cheese")) {
            pizza = new BJCheesePizza
        }

        pizza
    }
}
