package com.ffl.study.flink.scala.stream.streamapi.function

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author lff
  * @datetime 2020/12/06 00:50
  *
  *           需求：10s类温度连续上升则预警
  */
object ProcessFuncTest {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val stream: DataStream[String] = env.socketTextStream("localhost", 7777)

        //需求： 对于温度传感器，如果温度差超过10度，则报警
        val stream1: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        stream1.keyBy(_.id)
          .process(new MyKeyedProcessFunction)
          .print()

        env.execute("ProcessFuncTest")
    }

}


class TempIncreaseProcessFunction(interval: Long) extends KeyedProcessFunction[String, SensorReading, String] {

    // 保存上一个温度值进行比较，
    lazy val lastTempState: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("lastTempState", classOf[Double]))

    lazy val timeTsState: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("timeTsState", classOf[Long]))

    private var lastTemp: Double = _

    private var timeTs: Long = _

    override def processElement(value: SensorReading, ctx: KeyedProcessFunction[String, SensorReading, String]#Context, out: Collector[String]): Unit = {

        lastTemp = lastTempState.value()
        timeTs = ctx.timestamp()

        // 当前温度和上次进行比较
        if (value.temperature > lastTemp && timeTs == 0) {
            val ts = ctx.timerService().currentProcessingTime() + interval
            ctx.timerService().registerProcessingTimeTimer(ts)
            timeTsState.update(ts)
            lastTempState.update(lastTemp)
        } else if (value.temperature < lastTemp) {
            // 如果温度下降，则删除定时器
            ctx.timerService().deleteEventTimeTimer(timeTsState.value())
            timeTsState.clear()
        }
    }

    override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
        out.collect("传感器" + ctx.getCurrentKey + "温度连续" + interval / 1000 + "秒上升")
        timeTsState.clear()
    }
}


// 功能测试
class MyKeyedProcessFunction extends KeyedProcessFunction[String, SensorReading, String] {

    var myState: ValueState[Int] = _


    override def open(parameters: Configuration): Unit = {
        myState = getRuntimeContext.getState(new ValueStateDescriptor[Int]("valueState", classOf[Int]))
    }

    override def processElement(value: SensorReading, ctx: KeyedProcessFunction[String, SensorReading, String]#Context, out: Collector[String]): Unit = {
        ctx.timerService().registerEventTimeTimer(ctx.timestamp() + 6000)
    }

    override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = super.onTimer(timestamp, ctx, out)
}