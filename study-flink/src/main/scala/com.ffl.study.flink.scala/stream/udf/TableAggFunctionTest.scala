package com.ffl.study.flink.scala.stream.udf


import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.scala._
import org.apache.flink.table.functions.TableAggregateFunction
import org.apache.flink.types.Row
import org.apache.flink.util.Collector

/**
  * @author lff
  * @datetime 2020/12/08 08:35
  */
object TableAggFunctionTest {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        val settings: EnvironmentSettings = EnvironmentSettings
          .newInstance()
          .useOldPlanner()
          .inStreamingMode()
          .build()

        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env, settings)

        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"
        val stream: DataStream[String] = env.readTextFile(filePath)

        val dataStream: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(5)) {
            override def extractTimestamp(element: SensorReading): Long = element.timestamp * 1000L
        })

        val sensorTable: Table = tableEnv.fromDataStream(dataStream, 'id, 'timestamp.rowtime as 'ts, 'temperature)

        //1.table api
        val top2Temp: Top2Temp = new Top2Temp

        val resultTable: Table = sensorTable
          .groupBy('id)
          .flatAggregate(top2Temp('temperature) as('temp, 'rank))
          .select('id, 'temp, 'rank)

        resultTable.toRetractStream[Row].print("resultTable")

        env.execute("TableAggFunctionTest")
    }

}

// 自定义表聚合函数，提取所有温度值中的最高两个温度,输出(temp,rank)
class Top2Temp extends TableAggregateFunction[(Double, Int), Top2TempAcc] {

    override def createAccumulator(): Top2TempAcc = new Top2TempAcc

    def accumulate(acc: Top2TempAcc, temp: Double): Unit = {
        // 判断当前温度值是否比状态中大

        if (temp > acc.highestTemp) {
            acc.secondHighestTemp = acc.highestTemp
            acc.highestTemp = temp
        } else if (temp > acc.secondHighestTemp) {
            acc.secondHighestTemp = temp
        }
    }

    def emitValue(acc: Top2TempAcc, out: Collector[(Double, Int)]): Unit = {
        out.collect((acc.highestTemp, 1))
        out.collect((acc.secondHighestTemp, 2))
    }
}

// 定义一个类用来表示表聚合函数的状态
class Top2TempAcc {

    var highestTemp: Double = Double.MinValue

    var secondHighestTemp: Double = Double.MinValue
}