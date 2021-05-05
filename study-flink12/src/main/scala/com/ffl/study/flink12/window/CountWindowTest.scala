package com.ffl.study.flink12.window

import java.time.Duration

import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows

/**
  * @author lff
  * @datetime 2021/05/04 16:38
  */
object CountWindowTest {

    private val CLASS_NAME: String = CountWindowTest.getClass.getName

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val ds: DataStream[String] = env.socketTextStream("localhost", 7777)

        ds
          .map(item => {
              val arr: Array[String] = item.split(",")
              Tuple4(arr(0), arr(1), arr(2).toInt, arr(3).toLong)
          })
          .assignTimestampsAndWatermarks(
              WatermarkStrategy
                .forBoundedOutOfOrderness[(String, String, Int, Long)](Duration.ofSeconds(20))
                .withTimestampAssigner(new SerializableTimestampAssigner[(String, String, Int, Long)] {
                    override def extractTimestamp(element: (String, String, Int, Long), recordTimestamp: Long): Long = Math.max(element._4, recordTimestamp)
                }))
          .keyBy(_._1)
            .window(GlobalWindows.create())


        env.execute(CLASS_NAME)
    }


    case class Order(user: String, product: String, amount: String)

}
