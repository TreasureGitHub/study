package com.ffl.study.flink.scala.stream.state

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author lff
  * @datetime 2020/12/06 11:26
  */
object SideOutPutTest {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val stream: DataStream[String] = env.socketTextStream("localhost", 7777)

        //需求： 对于温度传感器，如果温度差超过10度，则报警
        val stream1: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        val highTempStream: DataStream[SensorReading] = stream1.process(new SplitTempProcess(30.0))

        highTempStream.print("high")
        highTempStream.getSideOutput(new OutputTag[(String,Long,Double)]("low")).print("low")

        env.execute("SideOutPutTest")
    }
}


class SplitTempProcess(threshold:Double) extends ProcessFunction[SensorReading,SensorReading]{

    override def processElement(value: SensorReading, ctx: ProcessFunction[SensorReading, SensorReading]#Context, out: Collector[SensorReading]): Unit = {
        // 如果当前温度大于指定值，输出主流
        if(value.temperature > threshold){
            out.collect(value)
        } else {
          // 如果不超过 指定值，输出测出流
            ctx.output(new OutputTag[(String,Long,Double)]("low"),(value.id,value.timestamp,value.temperature))
        }
    }
}