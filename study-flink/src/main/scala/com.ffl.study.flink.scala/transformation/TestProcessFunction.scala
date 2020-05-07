package com.ffl.study.flink.scala.transformation

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.util.Collector

/**
  * @author lff
  * @datetime 2020/04/24 20:50
  */
object TestProcessFunction {

    /**
      * 监控所有的手机号码，如果号码在5秒内，所有呼叫的日志都是失败的，则发出告警
      * 如果在5秒内只要有一个呼叫不是失败的，则不用告警
      *
      * @param args
      */
    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[StationLog] = env.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).trim.toLong, arr(5).trim.toLong)
          })

        val result: DataStream[String] = stream
          .keyBy(_.callIn)
          .process(new MonitorCallFail)

        result.print()

        env.execute("TestProcessFunction")

    }


    class MonitorCallFail extends KeyedProcessFunction[String, StationLog, String] {

        // 使用状态对象来记录时间
        lazy val timeState: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("time", classOf[Long]))

        override def processElement(value: StationLog, ctx: KeyedProcessFunction[String, StationLog, String]#Context, out: Collector[String]): Unit = {
           // 从状态中取得时间
            val time: Long = timeState.value()

            // 第一次发现呼叫失败，记录当前时间
            if(time == 0 && value.callType.equals("fail")){
                // 获取当前时间
                val nowTime: Long = ctx.timerService().currentProcessingTime()

                // 定时器在5秒后触发
                val onTime = nowTime + 5 * 1000L
                ctx.timerService().registerProcessingTimeTimer(onTime)

                // 将触发时间保存在状态中
                timeState.update(onTime)
            }

            // 表示有一次成功的，必须要删除定时器
            if(time != 0 && ! value.callType.equals("fail")){
                print(value)
                ctx.timerService().deleteProcessingTimeTimer(time)
                timeState.clear() // 清空状态中的时间
            }
        }

        // 时间到了，定时器执行
        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, StationLog, String]#OnTimerContext, out: Collector[String]): Unit = {
            val warnStr = "触发时间:" + timestamp + " 手机号 ：" + ctx.getCurrentKey

            out.collect(warnStr)
            timeState.clear()
        }
    }

}
