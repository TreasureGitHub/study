package com.ffl.study.flink.scala.transformation

import java.text.SimpleDateFormat
import java.util.Date

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/20 22:50
  */
object TestFunctionClass {


    // 计算出每个通话成功的日志中呼叫起始时间和结束时间，并且按照指定格式
    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val filePath = getClass.getResource("/station.log").getPath

        // 读取数据源
        val stream: DataStream[StationLog] = env.readTextFile(filePath)
          .map(
              line => {
                  val arr = line.split(",")
                  new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
              }
          )

        val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val result: DataStream[String] = stream.filter(_.callType.equals("success"))
          .map(new MyMapFunction(format))


        result.print()

        env.execute("TestFunctionClass")

    }

    class MyMapFunction(format:SimpleDateFormat) extends MapFunction[StationLog,String] {

        override def map(value: StationLog): String = {
            val startTime: Long = value.callTime
            val endTime:Long = startTime + value.duration * 1000

            "主叫号码：" + value.callOut + ",被叫号码:" + value.callIn + ",呼叫起始时间：" + format.format(new Date(startTime)) + ",呼叫结束时间：" + format.format(new Date(endTime))
        }
    }

}
