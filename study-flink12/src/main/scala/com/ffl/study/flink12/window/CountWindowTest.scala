package com.ffl.study.flink12.window

import java.lang
import java.time.Duration

import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows
import org.apache.flink.streaming.api.windowing.evictors.Evictor
import org.apache.flink.streaming.api.windowing.triggers.{CountTrigger, PurgingTrigger}
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue
import org.apache.flink.util.Collector

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
          .window(GlobalWindows.create)
          .trigger(PurgingTrigger.of(CountTrigger.of[GlobalWindow](1)))
          //.trigger(new Trigger[(String, String, Int, Long), GlobalWindow] {
          //
          //    override def onElement(element: (String, String, Int, Long), timestamp: Long, window: GlobalWindow, ctx: Trigger.TriggerContext): TriggerResult = {
          //        TriggerResult.FIRE
          //    }
          //
          //    override def onProcessingTime(time: Long, window: GlobalWindow, ctx: Trigger.TriggerContext): TriggerResult = {
          //        TriggerResult.CONTINUE
          //    }
          //
          //    override def onEventTime(time: Long, window: GlobalWindow, ctx: Trigger.TriggerContext): TriggerResult = {
          //        TriggerResult.CONTINUE
          //    }
          //
          //    override def clear(window: GlobalWindow, ctx: Trigger.TriggerContext): Unit = {
          //
          //    }
          //})
          .evictor(new Evictor[(String, String, Int, Long), GlobalWindow] {

            override def evictBefore(elements: lang.Iterable[TimestampedValue[(String, String, Int, Long)]], size: Int, window: GlobalWindow, evictorContext: Evictor.EvictorContext): Unit = {

            }

            override def evictAfter(elements: lang.Iterable[TimestampedValue[(String, String, Int, Long)]], size: Int, window: GlobalWindow, evictorContext: Evictor.EvictorContext): Unit = {

            }
        })
          .process(new ProcessWindowFunction[(String, String, Int, Long), (String, String, Int, Long), String, GlobalWindow] {
              override def process(key: String, context: Context, elements: Iterable[(String, String, Int, Long)], out: Collector[(String, String, Int, Long)]): Unit = {
                  elements.foreach(item => out.collect(item))
              }
          })
          .print()


        env.execute(CLASS_NAME)
    }


}
