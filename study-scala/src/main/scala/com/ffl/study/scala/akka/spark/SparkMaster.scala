package com.ffl.study.scala.akka.spark

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

/**
  * @author liufeifei  2019/12/22 19:22
  *
  */
class SparkMaster extends Actor {

    // 存储worker信息
    private val id2WorkerInfo = collection.mutable.HashMap[String, WorkInfo]()

    override def receive: Receive = {
        case RegisterWorkerInfo(workId, core, ram) => {
            // 将work的信息存储 起来
            val workInfo = new WorkInfo(workId, core, ram)

            if( !id2WorkerInfo.contains(workId)){
                id2WorkerInfo += ((workId,workInfo))

                //
                sender() ! RegisteredWorkerInfo
            }
        }

        case HeartBeat(workId) => {
            // master 收到worker的心跳 消息后 更新worker的上一次心跳时间
            val workInfo = id2WorkerInfo(workId)
            // 更改worker心跳时间
            val currentTime = System.currentTimeMillis()
            workInfo.lastHeartBeatTime = currentTime
        }

        case CheckTimeOutWorker => {
            context.system.scheduler.schedule(0 millis,6000 millis,self,RemoveTimeOutWorker)
        }

        case RemoveTimeOutWorker => {
            val workerInfos = id2WorkerInfo.values

            val currentTime = System.currentTimeMillis()

            workerInfos
              .filter( workerInfo => currentTime -  workerInfo.lastHeartBeatTime > 3000)
              .foreach(workerInfos => id2WorkerInfo.remove(workerInfos.id))

            println(s"还剩 ${workerInfos.size} 存活worker数量")
        }

    }
}

object SparkMaster {

    def main(args: Array[String]): Unit = {
        if (args.length != 3) {
            println(
                """
                  ||请输入参数:<host> <port> <masterName>
                """.stripMargin)

            sys.exit();
        }

        val host = args(0)
        val port = args(1)
        val masterName = args(2)

        val config = ConfigFactory.parseString(
            s"""
               |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
               |akka.remote.netty.tcp.hostname = $host
               |akka.remote.netty.tcp.port = $port
        """.stripMargin)

        val actorSystem: ActorSystem = ActorSystem("sparkMaster", config)
        val masterActorRef = actorSystem.actorOf(Props[SparkMaster],masterName);

        // 自己给自己发送一个消息，去启动一个调度器，定期的检测 HashMap中超时的worker
        masterActorRef ! CheckTimeOutWorker
    }
}
