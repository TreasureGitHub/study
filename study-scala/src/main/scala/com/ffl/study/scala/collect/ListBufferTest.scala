package com.ffl.study.scala.collect

import com.ffl.study.common.constants.StringConstants

import scala.collection.mutable.ListBuffer

/**
  * @author lff
  * @datetime 2020/11/25 23:12
  */
object ListBufferTest {

    def main(args: Array[String]): Unit = {
        //1.创建 ListBuffer
        val list1: ListBuffer[Int] = ListBuffer[Int]()
        val list2: ListBuffer[Int] = new ListBuffer[Int]()

        // 2.添加
        list1.append(5)
        list1.append(8)
        list1.append(7)
        list2 += 16
        println("list1 = " + list1)

        // 3.改
        list1(0) = 1
        println("调整后 list1 = " + list1)

        // 4.合并
        list1 ++= list2

        // 合并到list1上
        println("合并后 list1 = " + list1)
        // list2保持不变
        println("合并后 list2 = " + list2)


        // list3和list1其实是一个东东，没有生成新对象
        val list3: ListBuffer[Int] = list1 ++= list2
        println("list3 = " + list3)
        println(list3 == list1)

        // 生成了新对象
        val list4 = list1 :+ 5 // list1 不变 list4(1, 2, 3,4,5,5)
        println("list4 = " + list4)
        println("list1 = " + list1)

        // 5.遍历
        println("---------------------- 遍历 -----------------------")
        for (elem <- list1) {
            print(elem + StringConstants.TAB)
        }

        println()

        for (index <- 0 until list1.size) {
            print(list1(index) + StringConstants.TAB)
        }
        println()

        // 6.删除
        list1.remove(1)
    }
}
