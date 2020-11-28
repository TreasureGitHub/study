package com.ffl.study.scala.collect

import java.util

import scala.collection.mutable.ArrayBuffer

/**
  * @author lff
  * @datetime 2020/11/25 22:27
  */
object ArrayBuffer2JavaList {

    def main(args: Array[String]): Unit = {

        // 1. scala转java
        val arr: ArrayBuffer[String] = ArrayBuffer("1", "2", "3")

        import scala.collection.JavaConversions.bufferAsJavaList
        val javaArr = new ProcessBuilder(arr)

        // 这里 arrList 就是 java 中的 List
        val arrList: util.List[String] = javaArr.command()
        println(arrList) //输出 [1, 2, 3]

        // 2.java转scala
        //java 的 List 转成 scala 的 ArrayBuffer
        // 说明
             //1. asScalaBuffer 是一个隐式函数
        /*
                implicit def asScalaBuffer[A](l : java.util.List[A]) : scala.collection.mutable.Buffer[A] = { /* compiled
             code */ } */


        import scala.collection.JavaConversions.asScalaBuffer
        import scala.collection.mutable
        // java.util.List ==> Buffer
        val scalaArr: mutable.Buffer[String] = arrList
        scalaArr.append("jack")
        scalaArr.append("tom")
        scalaArr.remove(0)
        println (scalaArr) // (2,3,jack,tom)
    }
}
