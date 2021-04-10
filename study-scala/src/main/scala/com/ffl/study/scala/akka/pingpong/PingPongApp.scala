package com.ffl.study.scala.akka.pingpong

import akka.actor
import akka.actor.{ActorSystem, Props}

/**
  * @author liufeifei  2019/12/15 22:33
  *
  */
object PingPongApp {

    def main(args: Array[String]): Unit = {

        val pingPongSystem = ActorSystem("PingPongSystem");

        val ffActorRef = pingPongSystem.actorOf(Props[FengGeActor], "ff")

        //         创建龙哥 actorRef
        val mmActorRef = pingPongSystem.actorOf(actor.Props(new LongGeActor(ffActorRef)), "mm")

        ffActorRef ! "start"
        mmActorRef ! "start"

    }

}
