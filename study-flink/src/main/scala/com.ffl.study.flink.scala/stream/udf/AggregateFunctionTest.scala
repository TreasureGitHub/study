package com.ffl.study.flink.scala.stream.udf

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.functions.AggregateFunction
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/12/07 23:13
  */
object AggregateFunctionTest {

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
        val avgTemp = new AvgTemp()

        val resultTable: Table = sensorTable
          .groupBy('id)
          .aggregate(avgTemp('temperature) as 'avgTemp)
          .select('id, 'avgTemp)


        tableEnv.createTemporaryView("sensor",sensorTable)
        tableEnv.registerFunction("avgTemp",avgTemp)

        val resultSqlTable = tableEnv.sqlQuery(
            """
              |select id,avgTemp(temperature) from sensor group by id
            """.stripMargin)


        resultTable.toRetractStream[Row].print("resultTable")
        resultSqlTable.toRetractStream[Row].print("resultSqlTable")

        env.execute("TableFunctionTest")

    }
}

/**
  * 自定义聚合函数，求每个传感器的平均温度值
  *
  * @param factor
  */
class AvgTemp extends AggregateFunction[Double, AvgTempAcc] {

    override def getValue(accumulator: AvgTempAcc): Double = accumulator.sum / accumulator.count

    override def createAccumulator(): AvgTempAcc = new AvgTempAcc

    def accumulate(accumulator: AvgTempAcc, temp: Double): Unit = {
        accumulator.sum += temp
        accumulator.count += 1
    }
}

/**
  * 用于保存状态值
  */
class AvgTempAcc {

    var sum: Double = _

    var count: Int = _
}