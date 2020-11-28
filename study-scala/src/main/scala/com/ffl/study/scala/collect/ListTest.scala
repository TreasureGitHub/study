package com.ffl.study.scala.collect

/**
  * @author lff
  * @datetime 2020/11/25 22:46
  */
object ListTest {

    def main(args: Array[String]): Unit = {
        // 1.创建
        //说明
        //1.1 在默认情况下 List 是 scala.collection.immutable.List,即不可变
        //1.2 在 scala 中,List 就是不可变的，如需要使用可变的 List,则使用 ListBuffer
        //1.3. List 在 package object scala 做了 val List = scala.collection.immutable.List
        //1.4. val Nil = scala.collection.immutable.Nil // List()
        val list1: List[Int] = List(1, 2, 3)
        println(list1)

        val list2 = Nil
        println(list2)

        // 2.访问
        println(list1(0))
        println(list1.head)
        println(list1.tail)

        println("-------------list 追加元素后的效果-----------------")
        // 3.追加
        // 3.1 在前面增加
        val list3 = List(1, 2, 3, "abc")
        val list4 = 4 +: list3
        println(list4) // 得到新的list
        println(list3) // 老的list没有变化

        // 3.2 在后面追加
        println(list3 :+ 4)

        // 3.2
        val list5 = List(1, 2, 3, "abc")
        //说明 vallist6=4::5::6::list4::Nil 步骤
        // 1. List()
        //2. List(List(1, 2, 3, "abc"))
        //3. List(6,List(1, 2, 3, "abc"))
        //4. List(5,6,List(1, 2, 3, "abc"))
        //5. List(4,5,6,List(1, 2, 3, "abc"))
        val list6 = 4 :: 5 :: 6 :: list5 :: Nil
        println("list6=" + list6)


        //说明 vallist6=4::5::6::list4:::Nil 步骤
        // 1. List()
        //2. List(1, 2, 3, "abc")
        //3. List(6,1, 2, 3, "abc")
        //4. List(5,6,1, 2, 3, "abc")
        //5. List(4,5,6,1, 2, 3, "abc")
        val list7 = 4 :: 5 :: 6 :: list4 ::: Nil
        println("list7=" + list7)


    }
}
