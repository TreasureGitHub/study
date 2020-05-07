package com.ffl.study.flink.scala.transformation

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.{DataStream, OutputTag, StreamExecutionEnvironment}
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._

/**
  * @author lff
  * @datetime 2020/04/25 20:52
  */
object TestSideOutputStream {

    private val notSuccessTag = new OutputTag[StationLog]("not_success")  // 不成功的侧流标签

    // 将呼叫成功的日志输出到主流，不成功的输出到侧流
    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val filePath = getClass.getResource("/station.log").getPath

        // 读取数据源
        val stream: DataStream[StationLog] = env.readTextFile(filePath)
          .map(
              line => {
                  val arr = line.split(",")
                  new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
              }
          )

        val result: DataStream[StationLog] = stream.process(new CreateSideOutputStream(notSuccessTag))

        result.print("主流")
        // 一定要根据主流得到侧流
        val sideStream: DataStream[StationLog] = result.getSideOutput(notSuccessTag)

        sideStream.print("侧流")

        env.execute("TestSideOutputStream")
    }

    class CreateSideOutputStream(tag: OutputTag[StationLog]) extends ProcessFunction[StationLog, StationLog] {

        override def processElement(value: StationLog, ctx: ProcessFunction[StationLog, StationLog]#Context, out: Collector[StationLog]): Unit = {

            // 输出主流
            if (value.callType.equals("success")) {
                out.collect(value)
            } else { // 输出侧流
                ctx.output(tag, value)
            }
        }
    }

}
