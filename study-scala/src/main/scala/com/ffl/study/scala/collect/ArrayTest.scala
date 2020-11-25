package com.ffl.study.scala.collect

import com.ffl.study.common.constants.StringConstants

import scala.collection.mutable.ArrayBuffer

/**
  * @author lff
  * @datetime 2020/11/25 21:52
  */
object ArrayTest {

    def main(args: Array[String]): Unit = {

        // 不可变数组
        // 第一种 创建方法
        //1. 创建了一个 Array 对象,
        //2. [Int] 表示泛型，即该数组中，只能存放 Int
        // 3. [Any] 表示 该数组可以存放任意类型
        //4. 在没有赋值情况下，各个元素的值 0
        //5. arr01(3) = 7 表示修改 第 4 个元素的值
        val arr1 = new Array[Int](4)  //底层 int[] arr01 = new int[4]
        println(arr1.length) // 4
        println(arr1(0))  // 0

        // 数据遍历
        for (elem <- arr1) {
            println(elem)
        }

        // 第二种创建方法
        // 说明
        // 1. 使用的是 object Array 的 apply
        // 2. 直接初始化数组，这时因为你给了 整数和 "", 这个数组的泛型就 Any
        // 3. 遍历方式一样
        val arr2 = Array(1, 3, "xx")
        arr2(1) = "xxx"
        for (elem <- arr2) {
            println(elem)
        }

        //可以使用我们传统的方式遍历，使用下标的方式遍历
        for (index <- 0 until arr2.length) {
            printf("arr02[%d]=%s", index , arr2(index) + StringConstants.TAB + StringConstants.NEW_LINE)
        }

        // 可变数组
        val buffer: ArrayBuffer[Any] = ArrayBuffer[Any]()

        // 增
        buffer.append("hello","scala",1)

        // 取首个元素
        println(buffer.head)
        // 末个元素，除了首个元素都是末尾元素
        println(buffer.tail)

        buffer(1) = 89
        println(buffer)

        //删除,是根据下标删除
        buffer.remove(0) //

    }
}
