package com.ffl.study.flink.scala.old.state

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/25 21:29
  *
  * 第二种方法的实现，统计每个手机的呼叫时间间隔，单位为毫秒
  */
object TestKeyedState2 {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        import org.apache.flink.api.scala._
        env.setParallelism(1)

        val filePath: String = getClass.getResource("/station.log").getPath

        val stream: DataStream[StationLog] = env.readTextFile(filePath)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        stream
          .keyBy(_.callOut) // 分组
          // 有两种情况1、状态中有上一次的通话时间，2、没有 采用scala中模式匹配
          .mapWithState[(String, Long), StationLog] {
            case (in: StationLog, None) => ((in.callOut, 0), Some(in))
            case (in: StationLog, pre: Some[StationLog]) => { // 状态中有值
                val interval = Math.abs(in.callTime - pre.get.callTime)
                ((in.callOut, interval), Some(in))
            }
        }
          .filter(_._2 != 0)
          .print()

        env.execute("TestKeyedState2")
    }
}
