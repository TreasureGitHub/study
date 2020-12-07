package com.ffl.study.flink.scala.stream.tableapi

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.scala.{StreamTableEnvironment, _}
import org.apache.flink.table.api.{EnvironmentSettings, Table, Tumble}
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/12/06 23:14
  */
object TimeAndWindowTest {

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
        }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(5)){
            override def extractTimestamp(element: SensorReading): Long = element.timestamp * 1000L
        })

        val sensorTable: Table = tableEnv.fromDataStream(dataStream, 'id, 'timestamp.rowtime as 'ts,'temperature)

        // 1.group Window
        // 1.table api

        val resultTable: Table = sensorTable
          .window(Tumble over 10.seconds on 'ts as 'tw)  // 每10秒统计
          .groupBy('id, 'tw)
          .select('id, 'id.count, 'temperature.avg, 'tw.end)


        // 2.table sql
        tableEnv.createTemporaryView("sensor",sensorTable)
        val resultSqlTable = tableEnv.sqlQuery(
            """
              |select
              | id,
              | count(id),
              | avg(temperature),
              | tumble_end(ts,interval '10' second)
              |from sensor
              |group by
              | id,
              | tumble(ts,interval '10' second)
            """.stripMargin
        )

        resultTable.toAppendStream[Row].print("resultTable")
        resultSqlTable.toAppendStream[Row].print("sql")



        //sensorTable.printSchema()
        //sensorTable.toAppendStream[Row].print("table")

        env.execute("TimeAndWindowTest")
    }

}
