package com.ffl.study.scala.custom.service

import com.ffl.study.scala.custom.bean.Customer

import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

/**
  * @author lff
  * @datetime 2020/05/31 20:49
  */
class CustomerService {

    var custNum = 1

    val customers = ArrayBuffer(new Customer(1, "tom", '男', 20, "1234567689", "test@163.com"))

    def list(): ArrayBuffer[Customer] = {
        this.customers
    }

    def add(customer: Customer): Boolean = {
        custNum += 1
        customer.id = custNum
        customers += customer
        true
    }

    def findIndexById(id: Int): Int = {
        var index = -1

        breakable {
            for (i <- 0 until customers.size) {
                if (customers(i) == id) {
                    index = i
                    break()
                }
            }
        }

        index
    }

    def delete(id: Int): Boolean = {
        val index: Int = findIndexById(id)
        if (index != -1) {
            customers.remove(index)
            true
        } else {
            false
        }
    }

}
