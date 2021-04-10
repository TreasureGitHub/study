package com.ffl.study.scala.akka.robot

/**
  * @author liufeifei  2019/12/18 23:04
  *
  */
// 服务端发送给客户端的消息格式
case class ServerMessage(msg:String)

// 客户端发送给服务端的消息格式
case class ClientMessage(msg:String)
