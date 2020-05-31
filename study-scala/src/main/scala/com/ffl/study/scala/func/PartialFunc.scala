package com.ffl.study.scala.func

/**
  * @author lff
  * @datetime 2020/05/31 13:09
  *
  *           List(1,2,3,4,"hello")
  *
  *           有一个集合，要求忽略掉非数字元素 返回新集合，且对集合中元素都加1
  */
object PartialFunc {

    def main(args: Array[String]): Unit = {
        val list = List(1, 2, 3, 4, "hello")

        //1. filter + map
        val ints: List[Int] = list
          .filter(_.isInstanceOf[Int])
          .map(_.asInstanceOf[Int] + 1)
        println(ints)

        //2.模式匹配，不够完美
        val list1: List[Any] = list.
          map({
              case x: Int => x + 1
              case _ =>
          })
        println(list1)

        //3. 定义偏函数 并且 调用
        val partialFunc: PartialFunction[Any, Int] = new PartialFunction[Any, Int] {

            // 如果返回真则调用apply，否则不调用
            override def isDefinedAt(x: Any): Boolean = x.isInstanceOf[Int]

            override def apply(v1: Any): Int = v1.asInstanceOf[Int] + 1
        }

        val ints1: List[Int] = list.collect(partialFunc)
        println(ints1)

        // 偏函数简化一
        def partialFunc1: PartialFunction[Any, Int] = {
            case i: Int => i + 1
        }

        val ints2: List[Int] = list.collect(partialFunc1)
        println(ints2)

        // 偏函数简化二
        val ints3: List[Int] = list.collect { case i: Int => i + 1 }
        println(ints3)
    }
}
