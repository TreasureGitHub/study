package com.ffl.study.flink.scala.stream.udf

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.functions.TableFunction
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/12/07 23:13
  */
object TableFunctionTest {

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
        val split = new Split(StringConstants.UNDER_LINE)
        val resultTable: Table = sensorTable
          .joinLateral(split('id) as('word, 'length))
          .select('id, 'ts, 'word, 'length)

        tableEnv.createTemporaryView("sensor",sensorTable)
        tableEnv.registerFunction("split",split)

        val resultSqlTable = tableEnv.sqlQuery(
            """
              |select
              | id,ts,word,length
              |from
              | sensor,lateral table(split(id)) as splitid(word,length)
            """.stripMargin)

        resultTable.toAppendStream[Row].print("resultTable")
        resultSqlTable.toAppendStream[Row].print("resultSqlTable")

        env.execute("TableFunctionTest")

    }
}

/**
  * 自定义TableFunction
  *
  * @param factor
  */
class Split(separator:String) extends TableFunction[(String,Int)]{

    def eval(str:String): Unit = {

        str.split(separator).foreach(word => {
            collect((word,word.length))
        })
    }
}