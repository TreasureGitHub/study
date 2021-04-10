package com.ffl.study.scala.akka.spark

/**
  * @author liufeifei  2019/12/22 19:06
  *
  */

// work向master注册自己的信息
case class RegisterWorkerInfo(id: String, core: Int, ram: Int)

case class HeartBeat(id:String)

// master 向 worker 发送注册成功消息
case object RegisteredWorkerInfo

// worker发送给自己的消息，告诉自己说要周期性的向master发送心跳消息
case object SendHeartBeat

// master自己给自己发送一个检查超时worker的信息,并启动一个调度器，周期性检测
case object CheckTimeOutWorker

case object RemoveTimeOutWorker

class WorkInfo(val id: String, core: Int, ram: Int) {

    var lastHeartBeatTime:Long = _
}

