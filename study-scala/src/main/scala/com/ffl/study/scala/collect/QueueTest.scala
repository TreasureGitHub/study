package com.ffl.study.scala.collect

import scala.collection.mutable


/**
  * @author lff
  * @datetime 2020/11/26 00:03
  */
object QueueTest {

    def main(args: Array[String]): Unit = {
        // 1.创建队列
        val q1 = new mutable.Queue[Int]

        println(q1)
        // 2.添加元素
        q1 += 9
        println(q1)

        // 默认值直接加在队列后面
        q1 ++= List(4, 5, 7)
        println("q1=" + q1) //(9,4,5,7)

        //q1 += List(10,0) // 表示将 List(10,0) 作为一个元素加入到队列中

        //enQueue 入队列，默认是从队列的尾部加入. Redis
        q1.enqueue(100,10,100,888)
        println("q1=" + q1) // Queue(9, 4, 5, 7, 100,10,100,888)


        //3.取出元素
        //在队列中，严格的遵守，入队列的数据，放在队位，出队列的数据是队列的头部取出.
        //dequeue 从队列的头部取出元素 q1 本身会变
        val q2 = q1.dequeue()
        println("q2=" + q2 + "q1=/**/"+q1)
    }
}
