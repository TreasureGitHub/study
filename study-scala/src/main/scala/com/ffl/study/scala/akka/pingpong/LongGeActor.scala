package com.ffl.study.scala.akka.pingpong

import akka.actor.{Actor, ActorRef}

/**
  * @author liufeifei  2019/12/15 22:30
  *
  */
class LongGeActor(val fg: ActorRef) extends Actor {

    override def receive: Receive = {
        case "start" => {
            println("我准备好了")
            fg ! "啪"
        }
        case "啪啪" => {
            println("你真猛")
            Thread.sleep(1000)
            fg ! "啪"
        }
    }
}
