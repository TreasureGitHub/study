package com.ffl.study.flink.scala.stream.state


import java.util
import java.util.concurrent.TimeUnit

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.api.common.functions.{ReduceFunction, RichFlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.state._
import org.apache.flink.api.common.time.Time
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author lff
  * @datetime 2020/12/05 23:19
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
object StateTest {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val stream: DataStream[String] = env.socketTextStream("localhost", 7777)

        // 保存策略
        env.enableCheckpointing(60000L)
        env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE)
        env.getCheckpointConfig.setCheckpointTimeout(60000L)
        env.getCheckpointConfig.setMaxConcurrentCheckpoints(2) // 允许同时checkpoint的最大此时
        env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500) // checkpoint完成到下次开始checkpoint时间
        env.getCheckpointConfig.setPreferCheckpointForRecovery(true)
        env.getCheckpointConfig.setTolerableCheckpointFailureNumber(3) // 容忍checkpoint失败次数

        // 恢复策略
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3,1000L)) // 重启三次，每次间隔1000毫秒
        env.setRestartStrategy(RestartStrategies.failureRateRestart(5,Time.of(5,TimeUnit.MINUTES),Time.of(10,TimeUnit.MINUTES)))

        //需求： 对于温度传感器，如果温度差超过10度，则报警
        val stream1: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        val alertStream: DataStream[(String, Double, Double)] = stream1
          .keyBy(_.id)
          //.flatMap(new TempChangeAlert(10.0))
          .flatMapWithState[(String,Double,Double),Double]({
            case (data: SensorReading, None) => (List.empty, Some(data.temperature))
            case (data: SensorReading, lastTemp: Some[Double]) => {


                val diff: Double = (data.temperature - lastTemp.get).abs

                if (diff > 10.0) { // 需要加入初次状态判断
                    (List((data.id, lastTemp.get, data.temperature)), Some(data.temperature))
                } else {
                    (List.empty, Some(data.temperature))
                }
            }
        })

        alertStream.print()

        env.execute("StateTest")
    }
}

class TempChangeAlert(threshold: Double) extends RichFlatMapFunction[SensorReading, (String, Double, Double)] {

    lazy val lastTempState: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("lastTempState", classOf[Double]))

    override def flatMap(value: SensorReading, out: Collector[(String, Double, Double)]): Unit = {
        val lastTemp: Double = lastTempState.value()

        if ((value.temperature - lastTemp).abs > threshold) { // 需要加入初次状态判断
            out.collect((value.id, lastTemp, value.temperature))
        }

        lastTempState.update(value.temperature)
    }

}

// 必须定义在RichFunction中，因为需要运行时上下文   测试
class MyRichMapper extends RichMapFunction[SensorReading, String] {


    lazy val listState: ListState[Int] = getRuntimeContext.getListState(new ListStateDescriptor[Int]("listState", classOf[Int]))

    private val mapState: MapState[String, Double] = getRuntimeContext.getMapState(new MapStateDescriptor[String, Double]("mapState", classOf[String], classOf[Double]))

    lazy val reduceState = getRuntimeContext.getReducingState[SensorReading](new ReducingStateDescriptor[SensorReading]("reduceState", new ReduceFunction[SensorReading]() {
        override def reduce(value1: SensorReading, value2: SensorReading): SensorReading = SensorReading(value1.id, value1.timestamp, value1.temperature + value2.temperature)
    }, classOf[SensorReading]))

    // 定义状态
    var valueState: ValueState[Double] = _

    override def open(parameters: Configuration): Unit = {
        // 在这里进行操作
        valueState = getRuntimeContext.getState(new ValueStateDescriptor[Double]("valueState", classOf[Double]))
    }

    override def map(value: SensorReading): String = {
        // 1.value state
        valueState.update(value.temperature)

        // 2.list state

        val list = new util.ArrayList[Int]()
        list.add(2)
        list.add(3)
        listState.addAll(list)

        // 3.mapstate
        mapState.contains("sensor_1")
        mapState.get("sensor_1")
        mapState.put("sensor_1", 1)

        reduceState.add(value)

        value.id
    }
}
