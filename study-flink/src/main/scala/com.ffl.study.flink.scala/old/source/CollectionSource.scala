package com.ffl.study.flink.scala.old.source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment


/**
  * @author lff
  * @datetime 2020/04/18 19:29
  *
  */

/**
  * 基站日志
  *
  * @param sid      基站ID
  * @param callOut  主叫号码
  * @param callIn   被叫号码
  * @param callType 呼叫类型
  * @param callTime 呼叫时长(毫秒)
  * @param duration 呼叫时长(秒)
  */
case class StationLog(sid: String,var callOut: String,var callIn: String, callType: String, callTime: Long, duration: Long)

object CollectionSource {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        val logs = Array(
            new StationLog("001", "186", "189", "busy", System.currentTimeMillis(), 0),
            new StationLog("002", "186", "189", "busy", System.currentTimeMillis(), 0),
            new StationLog("004", "186", "189", "busy", System.currentTimeMillis(), 0),
            new StationLog("005", "186", "189", "busy", System.currentTimeMillis(), 0)
        )

        val stream: DataStream[StationLog] = env.fromCollection(logs)

        stream.print()

        env.execute("CollectionSource")
    }

}
