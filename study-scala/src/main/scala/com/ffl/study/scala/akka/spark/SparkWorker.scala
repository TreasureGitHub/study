package com.ffl.study.scala.akka.spark

import java.util.UUID

import scala.concurrent.duration._

/**
  * @author liufeifei  2019/12/22 18:49
  *
  */
class SparkWorker(masterUrl: String) extends Actor {


    private var masterProxy: ActorSelection = _

    val workId = UUID.randomUUID().toString

    override def preStart(): Unit = {
        masterProxy = context.actorSelection(masterUrl)
    }

    override def receive: Receive = {
        case "start" => {
            // 自己已就绪，向 master 注册自己的信息 id，core，memory
            println("------start-------")
            masterProxy ! RegisterWorkerInfo(workId, 4, 32 * 1024)
        }

        case  RegisteredWorkerInfo => { // master发送给自己的注册成功消息
            // workder启动定时器，定时向master 发送心跳
            context.system.scheduler.schedule(0 millis,1500 millis,self,SendHeartBeat)
        }

        case SendHeartBeat => {
            // 开始向master 发送心跳
            println(s"---------${workId}发送心跳--------")
            masterProxy ! HeartBeat(workId)
        }
    }
}

object SparkWorker {

    def main(args: Array[String]): Unit = {

        if (args.length != 4) {
            println(
                """
                  ||请输入参数:<host> <port> <workName> <masterUrl>
                """.stripMargin)

            sys.exit();
        }

        val host = args(0)
        val port = args(1)
        val workerName = args(2)
        val masterUrl = args(3)

        val config = ConfigFactory.parseString(
            s"""
               |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
               |akka.remote.netty.tcp.hostname = $host
               |akka.remote.netty.tcp.port = $port
        """.stripMargin)

        val actorSystem: ActorSystem = ActorSystem("sparkWorker", config)

        val workActorRef: ActorRef = actorSystem.actorOf(Props(new SparkWorker(masterUrl)), workerName)

        // 给自己发送已启动消息，表示自己已经启动就绪
        workActorRef ! "start"
    }
}
