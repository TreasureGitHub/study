package com.ffl.study.flink.scala.stream.window

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author lff
  * @datetime 2020/12/12 21:20
  */
object WindowTest1 {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)


        val stream: DataStream[String] = env.socketTextStream("localhost", 7777)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)   // 如果是处理时间的话默认是0，如果是事件事件的话默认为200ms
        println(env.getConfig.getAutoWatermarkInterval)   // 查看默认 的 间隔
        env.getConfig.setAutoWatermarkInterval(1000);

        val stream1: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(3)) {

            override def extractTimestamp(element: SensorReading): Long = element.timestamp * 1000

        })

        stream1.map(x => (x.id,x.temperature))
          .keyBy(0) // keyedStream
          .timeWindow(Time.minutes(5))
          //.evictor()
          .max(1)
          .print()


        env.execute("WindowTest1")

    }

}
