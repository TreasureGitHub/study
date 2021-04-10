package com.ffl.study.flink.scala.stream

import com.ffl.study.common.constants.StringConstants
import org.apache.flink.api.common.state.StateTtlConfig
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time


/**
  * @author lff
  * @datetime 2020/12/13 19:07
  */
object TestWaterMark {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        val ttlConfig = StateTtlConfig
          .newBuilder(org.apache.flink.api.common.time.Time.seconds(80)) //这是state存活时间10s
          .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)//设置过期时间更新方式
          .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)//永远不要返回过期的状态
          //.cleanupInRocksdbCompactFilter(1000)//处理完1000个状态查询时候，会启用一次CompactFilter
          .build

        val dataStream: DataStream[String] = env.socketTextStream("localhost", 7777)


        dataStream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            arr(0)
            new Tuple2[String, Long](arr(0), arr(1).toLong)
        }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[(String, Long)](Time.seconds(5)) {

            override def extractTimestamp(element: (String, Long)): Long = {
                print(element._2)
                element._2
            }
        }).filter(data => {
            !data._1.equals("test")
        }).addSink(new SinkFunction[(String, Long)] {
            override def invoke(value: (String, Long), context: SinkFunction.Context[_]): Unit = super.invoke(value, context)
        })


        env.execute(TestWaterMark.getClass.getSimpleName)
    }

}