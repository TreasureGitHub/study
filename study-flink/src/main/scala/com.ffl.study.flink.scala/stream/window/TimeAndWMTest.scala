package com.ffl.study.flink.scala.stream.window

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark

/**
  * @author lff
  * @datetime 2020/12/12 16:28
  */
object TimeAndWMTest {

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
        }).assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[SensorReading]() {

            private var maxEventTime:Long = _

            // 延迟三秒
            private val DELAY:Long = 3000;

            // 每隔  env.getConfig.getAutoWatermarkInterval 调用此方法生成 waterMark
            override def getCurrentWatermark: Watermark = {
                //println("---------------getCurrentWatermark--------------  : " + (maxEventTime - DELAY))
                new Watermark(maxEventTime - DELAY)
            }

            // 没进入一个元素调用此方法
            override def extractTimestamp(element: SensorReading, previousElementTimestamp: Long): Long = {
                //println("---------------extractTimestamp--------------  :" + element)
                maxEventTime = maxEventTime.max(element.timestamp * 1000)
                element.timestamp * 1000
            }
        })

        stream1.map(x => (1, 1))
          .keyBy(0)
          .sum(1)
          .print()


        env.execute("TimeTest")

    }

}
