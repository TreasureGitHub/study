package com.ffl.study.scala.sales

/**
  * @author lff
  * @datetime 2020/05/31 01:14
  *
  *     现在有一些商品，使用scala完成捆绑打折出售，要求
  * 1.商品绑定可以是单个商品，也可以是多个商品
  * 2.打折时按照折扣x元进行设计
  * 3.能够统计出所有商品打折后的最终价格
  */
object SalesDemo {

    def main(args: Array[String]): Unit = {

        val sale = Bundle("书籍", 10, Book("漫画", 40), Bundle("文学作品", 20, Book("阳光", 80), Book("围城", 30)))

        // 1.如果我们进行对象匹配时，不想接受某些值，则使用_忽略即可,_* 表示所有
        val res = sale match {
            case Bundle(_, _, Book(desc, _), _*) => desc
            case _ => null
        }

        println(res)

        // 2.通过@表示法 将嵌套的值绑定到变量 _* 绑定剩余item到rest
        val res2 = sale match {
            case Bundle(_, _, art@Book(_, _), rest@_*) => (art, rest)
            case _ => null
        }
        println(res2)

        // 3.不使用 _* 绑定剩余item到rest
        val res3 = sale match {
            case Bundle(_, _, art@Book(_, _), rest) => (art, rest)
            case _ => null
        }
        println(res3)

        println("price = " + price(sale))

    }

    def price(it: Item): Double = {
        it match {
            case Book(_, p) => p
            case Bundle(_, disc, its@_*) => its.map(price).sum - disc
        }
    }
}


// 项
abstract class Item

/**
  * 书籍
  *
  * @param description 书籍描述
  * @param price       价格
  */
case class Book(description: String, price: Double) extends Item

/**
  *
  * @param description 描述
  * @param discount    打折后的价格
  * @param item        item
  */
case class Bundle(description: String, discount: Double, item: Item*) extends Item