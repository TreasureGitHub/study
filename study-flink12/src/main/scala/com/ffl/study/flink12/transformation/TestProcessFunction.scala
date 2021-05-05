package com.ffl.study.flink12.transformation

import java.lang
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date

import org.apache.commons.lang3.StringUtils
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.state.{MapState, MapStateDescriptor, StateTtlConfig}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector


/**
  * @author lff
  * @datetime 2021/05/05 10:29
  *
  *           region,aaa,2,1619837624000			2021-05-01 10:53:44
  *           region,aaa,3,1619841224000			2021-05-01 11:53:44
  *           region,aaa,2,1619841324000			2021-05-01 11:55:24
  *           region,aaa,3,1619855624000			2021-05-01 15:53:44
  */
object TestProcessFunction {

    private val CLASS_NAME: String = TestProcessFunction.getClass.getName

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
          .process(new MyKeyedProcessFunction())
          .print("result")

        env.execute(CLASS_NAME)
    }

    class MyKeyedProcessFunction extends KeyedProcessFunction[String, (String, String, Int, Long), (String, String, Long, Long)] {

        private val size: Long = 1 * 60 * 60 * 1000

        private var mapState: MapState[String, String] = _

        private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH")

        override def open(parameters: Configuration): Unit = {
            val ttlConfig = StateTtlConfig
              .newBuilder(org.apache.flink.api.common.time.Time.days(1))
              .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
              .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
              .build
            val mapStateDesc = new MapStateDescriptor[String, String]("mapState", classOf[String], classOf[String])
            mapStateDesc.enableTimeToLive(ttlConfig)
            mapState = getRuntimeContext.getMapState(mapStateDesc)
        }

        override def processElement(value: (String, String, Int, Long), ctx: KeyedProcessFunction[String, (String, String, Int, Long), (String, String, Long, Long)]#Context, out: Collector[(String, String, Long, Long)]): Unit = {
            val wm: Long = ctx.timerService().currentWatermark()
            //println("watermark : " + wm)

            var hasUpdate: Boolean = false

            val userValue: String = mapState.get(value._2)


            if (mapState.isEmpty) {
                val timestamp: lang.Long = ctx.timestamp()
                println("timestamp" + timestamp)

                val endTime: Long = timestamp - (timestamp + size) % size + size
                ctx.timerService().registerEventTimeTimer(endTime)
            }

            if (StringUtils.isEmpty(userValue)) {
                mapState.put(value._2, value._3 + "," + value._4)
                hasUpdate = true
            } else {
                val arr: Array[String] = userValue.split(",")
                if (value._4 > arr(1).toLong) {
                    mapState.put(value._2, value._3 + "," + value._4)
                    hasUpdate = true
                }
            }

            if (hasUpdate) {
                var cnt2: Long = 0
                var cnt3: Long = 0
                mapState.values().forEach(item => {
                    val arr: Array[String] = item.split(",")
                    if (2 == arr(0).toInt) {
                        cnt2 += 1
                    } else if (3 == arr(0).toInt) {
                        cnt3 += 1
                    }
                })

                val date: String = sdf.format(new Date(ctx.timestamp()))
                out.collect(date, value._1, cnt2, cnt3)
            }
        }

        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, (String, String, Int, Long), (String, String, Long, Long)]#OnTimerContext, out: Collector[(String, String, Long, Long)]): Unit = {

            println("onTime currentWatermark " + ctx.timerService().currentWatermark())
            println("onTime timestamp " + timestamp)


            val date: String = sdf.format(new Date(timestamp))

            var cnt2: Long = 0
            var cnt3: Long = 0
            mapState.values().forEach(item => {
                val arr: Array[String] = item.split(",")
                if (2 == arr(0).toInt) {
                    cnt2 += 1
                } else if (3 == arr(0).toInt) {
                    cnt3 += 1
                }
            })

            out.collect(date, ctx.getCurrentKey, cnt2, cnt3)
            ctx.timerService().registerEventTimeTimer(timestamp + size)
        }
    }

}
