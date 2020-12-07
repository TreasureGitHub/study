package com.ffl.study.flink.scala.stream.window

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author lff
  * @datetime 2020/12/05 19:26
  *
  *           sensor_1,1547718199,35.8
  *           sensor_6,1547718201,15.4
  *           sensor_7,1547718202,6.7
  *           sensor_10,1547718205,38.1
  *           sensor_1,1547718206,23.2
  *           sensor_1,1547718208,19.9
  *           sensor_1,1547718210,33.8
  *           sensor_1,1547718213,30.9
  *           sensor_1,1547718212,10.9
  */
object WindowTest {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)


        val stream: DataStream[String] = env.socketTextStream("localhost",7777)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)   // 如果是处理时间的话默认是0，如果是事件事件的话默认为200ms
        env.getConfig.setAutoWatermarkInterval(50);

        val lateTag = new OutputTag[(String, Double, Long)]("late")

        val stream1: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })
          //.assignAscendingTimestamps(_.timestamp * 1000L) // 升序时间定义时间戳
            .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(3)) {

            override def extractTimestamp(element: SensorReading): Long = element.timestamp * 1000

        })

        val result: DataStream[(String, Double, Long)] = stream1
          .map(data => (data.id, data.temperature, data.timestamp))
          .keyBy(_._1) // 按照第一个元素分组
          .timeWindow(Time.seconds(15))
          .allowedLateness(Time.minutes(1))
          .sideOutputLateData(lateTag)
          //.window(TumblingEventTimeWindows.of(Time.seconds(5)))
          //.window(SlidingProcessingTimeWindows.of(Time.seconds(15), Time.seconds(5)))
          //  .timeWindow(Time.seconds(15))
          .reduce((currData, newData) => (currData._1, currData._2.min(newData._2), newData._3))

        //val stream1: WindowedStream[(String, Int), String, TimeWindow] = stream.map((_, 1))
        //  .keyBy(_._1)
        //  .timeWindow(Time.seconds(5))
        //
        //val result: DataStream[(String, Int)] = stream1.reduce((currData,newData) => (currData._1,currData._2 + 1))

        result.print()
        result.getSideOutput(lateTag).print("late")




        env.execute("WindowTest")
    }

}
