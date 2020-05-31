package com.ffl.study.scala.custom.view

import com.ffl.study.scala.custom.bean.Customer
import com.ffl.study.scala.custom.service.CustomerService

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

/**
  * @author lff
  * @datetime 2020/05/31 20:37
  */
class CustomerView {

    var loop = true

    var key = ' '

    val customerService = new CustomerService

    def mainMenu(): Unit = {
        do {
            println("---------------客户信息管理软件-------------")
            println("               1 添 加 客 户 ")
            println("               2 修 改 客 户 ")
            println("               3 删 除 客 户 ")
            println("               4 客 户 列 表")
            println("               5 退      出")
            println("请选择(1-5)")
            key = StdIn.readChar()

            key match {
                case '1' => add()
                case '2' => println(" 修 改 客 户 ")
                case '3' => delete()
                case '4' => list
                case '5' => loop = false
            }

        } while (loop)
    }

    /**
      * 查询
      */
    def list(): Unit = {
        println()
        println("-------------------客户列表---------------------")
        println("编号\t\t姓名\t\t性别\t\t年龄\t\t电话\t\t邮箱")
        val customers: ArrayBuffer[Customer] = customerService.list()

        for (customer <- customers) {
            println(customer)
        }

        println("-------------------客户列表完成---------------------")
    }

    /**
      * 添加
      */
    def add(): Unit = {
        println()
        println("-------------------添加客户---------------------")
        val customer = new Customer
        println("姓名")
        customer.name = StdIn.readLine()

        println("性别")
        customer.gender = StdIn.readChar()

        println("年龄")
        customer.age = StdIn.readShort()

        println("电话")
        customer.tel = StdIn.readLine()

        println("邮箱")
        customer.email = StdIn.readLine()

        customerService.add(customer)
    }

    def delete(): Unit = {
        println()
        println("-------------------删除客户---------------------")
        println("请选择待删除的客户编号(-1退出)：")
        val id: Int = StdIn.readInt()
        if (id == -1) {
            println("-------------------删除没有完成---------------------")
        }
        println("确认是否删除(Y/N):")

        val choice: String = StdIn.readLine().toLowerCase

        if (choice.equals("y")) {
            if (customerService.delete(id)) {
                println("-------------------删除完成---------------------")
                return
            }
        }

        println("-------------------删除没有完成---------------------")
    }

}
