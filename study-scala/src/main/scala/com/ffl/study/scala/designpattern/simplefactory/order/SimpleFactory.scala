package com.ffl.study.scala.designpattern.simplefactory.order

import com.ffl.study.scala.designpattern.simplefactory.pizza.{GreekPizza, PepperPizza, Pizza}

/**
  * @author lff
  * @datetime 2020/05/31 23:51
  */
object SimpleFactory {


    def createPizza(name: String): Pizza = {
        var pizza: Pizza = null
        if (name.equals("greek")) {
            pizza = new GreekPizza
        } else if (name.equals("pepper")) {
            pizza = new PepperPizza
        }

        return pizza
    }

}
