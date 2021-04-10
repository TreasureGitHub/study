package com.ffl.study.scala.akka.pingpong

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @author liufeifei  2019/12/10 23:16
  *
  */

class HelloActor extends Actor {

    override def receive: Receive = {
        case "你好帅" => println("净说实话")
        case "丑" => println("滚犊子！")
//        case i:String => println("测试")
        case "stop" => {
            context.stop(self)
            context.system.terminate()
        }
        case _ => println("无法匹配")
    }
}

object HelloActor {


    // 工厂
    private val nBFactory = ActorSystem("NBFactory")

    private val helloActorRef: ActorRef = nBFactory.actorOf(Props[HelloActor],"helloActor")

    def main(args: Array[String]): Unit = {
        helloActorRef ! "你好帅"
        helloActorRef ! "丑"


        helloActorRef ! "stop"
    }

}
