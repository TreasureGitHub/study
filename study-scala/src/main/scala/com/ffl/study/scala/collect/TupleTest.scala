package com.ffl.study.scala.collect

/**
  * @author lff
  * @datetime 2020/05/30 17:21
  */
object TupleTest {

    def main(args: Array[String]): Unit = {
        // 1.元组的创建
        //创建
        //说明 tuple1 就是一个 Tuple 类型是 Tuple5
        //简单说明: 为了高效的操作元组 ， 编译器根据元素的个数不同，对应不同的元组类型 // 分别 Tuple1----Tuple22
        val tuple1 = (1, 2, 3, "hello", 4)
        println(tuple1)

        // 2.访问
        println(tuple1._1)


        // 3.遍历
        //Tuple 是一个整体，遍历需要调其迭代器
        for (elem <- tuple1.productIterator) {
            println(elem)
        }
    }
}
