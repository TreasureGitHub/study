package com.ffl.study.scala.akka.robot

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

/**
  * @author liufeifei  2019/12/18 22:48
  *
  */
class ClientActor extends Actor {

    private var serverActorRef:ActorSelection = _

    override def preStart(): Unit = {
        serverActorRef = context.actorSelection("akka.tcp://Server@127.0.0.1:8878/user/shanshan")
    }

    override def receive: Receive = {
        case "start" => println("牛魔王系列已启动。。。")
        case msg: String => {
            serverActorRef ! ClientMessage(msg)
        }

        case ServerMessage(msg) => println(s"收到服务端消息${msg}")
    }
}

object ClientActor extends App {

    val host = "127.0.0.1"
    val port = 8880


    val config = ConfigFactory.parseString(
        s"""
           |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
           |akka.remote.netty.tcp.hostname = $host
           |akka.remote.netty.tcp.port = $port
        """.stripMargin)

    private val clientSystem = ActorSystem("client", config)

    private val clientActorRef = clientSystem.actorOf(Props[ClientActor], "NWM-002")

    clientActorRef ! "start"

    while (true) {
        val question = StdIn.readLine()

        clientActorRef ! question
    }
}