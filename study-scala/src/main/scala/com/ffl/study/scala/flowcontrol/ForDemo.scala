package com.ffl.study.scala.flowcontrol

import util.control.Breaks._

/**
  * @author lff
  * @datetime 2020/05/26 20:04
  *
  *           打出 9 9 乘法表
  */
object ForDemo {

    def main(args: Array[String]): Unit = {

        breakable(
            for (i <- 1 to 9) {
                for (j <- 1 to i) {
                    printf("%d * %d = %d  ", j, i, i * j)

                    if (j > 3) {
                        break()
                    }
                }

                println()
            }
        )
    }

}
