package com.ffl.study.flink.scala.state

import com.ffl.study.flink.scala.source.StationLog
import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.util.Collector

/**
  * @author lff
  * @datetime 2020/04/25 21:29
  *
  * 第二种方法的实现，统计每个手机的呼叫时间间隔，单位为毫秒
  */
object TestKeyedState1 {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        import org.apache.flink.api.scala._
        env.setParallelism(1)

        val filePath: String = getClass.getResource("/station.log").getPath

        val stream: DataStream[StationLog] = env.readTextFile(filePath)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        val result: DataStream[(String, Long)] = stream
          .keyBy(_.callOut) // 分组
          .flatMap(new CallIntervalFunction)

        result.print()
        env.execute("TestKeyedState1")
    }

    class CallIntervalFunction extends RichFlatMapFunction[StationLog, (String, Long)] {

        private var preCallTimeState: ValueState[Long] = _


        override def open(parameters: Configuration): Unit = {
            preCallTimeState = getRuntimeContext.getState(new ValueStateDescriptor[Long]("pre",classOf[Long]))
        }

        override def flatMap(value: StationLog, out: Collector[(String, Long)]): Unit = {
            // 从状态中取得前一次的呼叫时间
            val preCallTime = preCallTimeState.value()

            if(preCallTime == 0){
                preCallTimeState.update(value.callTime)
            } else { // 状态中有数据，则要计算时间间隔
                val interval = value.callTime - preCallTime

                out.collect((value.callOut,interval))
            }
        }
    }


}
