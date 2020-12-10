package com.ffl.study.flink.scala.stream.udf

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.functions.ScalarFunction
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/12/07 23:13
  */
object ScalaFunctionTest {

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
        val hashCode = new HashCode(2)

        val resultTable: Table = sensorTable.select('id,'ts,hashCode('id))

        tableEnv.createTemporaryView("sensor",sensorTable)
        tableEnv.registerFunction("hashCode",hashCode)

        // 调用hash函数对id进行hash运算
        val resultSqlTable = tableEnv.sqlQuery(
            """
               select id,ts,hashCode(id) from sensor
            """.stripMargin)

        resultTable.toAppendStream[Row].print("resultTable")
        resultSqlTable.toAppendStream[Row].print("resultSqlTable")

        env.execute("ScalaFunctionTest")

    }
}

/**
  * 自定义函数
  *
  * @param factor
  */
class HashCode(factor:Int) extends ScalarFunction {

    def eval(s:String): Int = {
        return s.hashCode * factor - 10000
    }
}