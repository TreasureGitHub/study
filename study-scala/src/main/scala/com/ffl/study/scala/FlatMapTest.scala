package com.ffl.study.scala



/**
  * @author lff
  * @datetime 2020/11/21 17:10
  */
object FlatMapTest {


    def main(args: Array[String]): Unit = {
        //val list = List(List("Alice", "Bob", "Nick"),List("test","test1"))

        val list = List("hello scala","hello")

        //val strs: List[String] = list.flatMap(x => x)

        //println(strs)

        val iterator: Iterator[String] = list.iterator


        val strings: Iterator[String] = iterator.flatMap(data => data.split(" "))

        //println(strings.toList)

        while(strings.hasNext){
            val str: String = strings.next()
            println(str)
        }




        //val lines = List("abc efg hij","123 456")
        //
        //val strs: List[String] = lines.flatMap(_.split(" "))
        //
        //var h: ::[String] = null

        //println(h)
        println()

        //println(h.getClass)



        //println(strs)

    }

}
