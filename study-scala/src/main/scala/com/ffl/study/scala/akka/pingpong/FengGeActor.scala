package com.ffl.study.scala.akka.pingpong

import akka.actor.Actor

/**
  * @author liufeifei  2019/12/15 22:31
  *
  */
class FengGeActor extends Actor {

    override def receive: Receive = {
        case "start" => println("峰峰说：I am Ok！")
        case "啪" => {
            println("那必须的")
            Thread.sleep(1000)
            sender() ! "啪啪"
        }
    }
}
