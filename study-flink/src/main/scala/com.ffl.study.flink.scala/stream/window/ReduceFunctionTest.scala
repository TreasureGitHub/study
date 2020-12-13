package com.ffl.study.flink.scala.stream.window

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow

/**
  *
  * 测试数据
  * sensor_1,1547718201,15.4
  * sensor_1,1547718202,6.7
  * sensor_1,1547718206,23.2
  * sensor_1,1547718208,19.9
  * sensor_1,1547718210,33.8
  * sensor_1,1547718212,10.9
  * sensor_1,1547718213,30.9
  *
  * @author lff
  * @datetime 2020/12/12 23:06
  */
object ReduceFunctionTest {


    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)


        val stream: DataStream[String] = env.socketTextStream("localhost", 7777)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)   // 如果是处理时间的话默认是0，如果是事件事件的话默认为200ms
        env.getConfig.setAutoWatermarkInterval(1000)

        val stream1: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        }).assignTimestampsAndWatermarks(new AscendingTimestampExtractor[SensorReading]() {
            override def extractAscendingTimestamp(element: SensorReading): Long = element.timestamp * 1000 // 设置有序的窗口
        })

        stream1.map(x => (x.id, 1L))
          .keyBy(0) // keyedStream
          .timeWindow(Time.seconds(10))
          .trigger(new Trigger[Any, TimeWindow]() {

              override def onElement(element: Any, timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
                  println("---------------onElement------------------------")
                  TriggerResult.FIRE
              }

              override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
                  println("---------------onProcessingTime------------------------")
                  TriggerResult.CONTINUE
              }

              override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
                  println("---------------onEventTime------------------------")
                  TriggerResult.CONTINUE
              }

              override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {
                  println("---------------clear------------------------")
              }
          })
          //.evictor()
          //.max(1)
          //.reduce()
          //.reduce((v1, v2) => (v1._1, v1._2 + v2._2) )
          .reduce(new MyReduceFunction)
          .filter(x => {
              println("------------emit filter-------------  "+ x)
              true
          })
          //.aggregate()
            .print("result")


        env.execute("WindowTest1")

    }

}


class MyReduceFunction extends ReduceFunction[(String, Long)] {

    override def reduce(value1: (String, Long), value2: (String, Long)): (String, Long) = {
        println("-------------------reduce---------------------")
        (value1._1, value1._2 + 1)

    }
}


