package com.ffl.study.flink.scala.stream.tableapi

import com.ffl.study.common.constants.StringConstants
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.scala._

/**
  * @author lff
  * @datetime 2020/12/06 17:10
  */
object Example {

    def main(args: Array[String]): Unit = {
        // 创建表执行环境
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)

        val stream: DataStream[String] = env.socketTextStream("localhost",7777)
        val dataStream: DataStream[SensorReading] = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        // 基于流创建一张表
        val dataTable: Table = tableEnv.fromDataStream(dataStream)


        // 调用table api进行转换
        val result: Table = dataTable
          .select("id,temperature")
          .filter("id == 'sensor_1'")

        // 直接用sql实现
        tableEnv.createTemporaryView("dataTable",dataTable)
        val sql:String = "select id,temperature from dataTable where id = 'sensor_1'"

        val table: Table = tableEnv.sqlQuery(sql)


        result.toAppendStream[(String,Double)].print()
        table.toAppendStream[(String,Double)].print()

        env.execute("TableApiTest")

    }

}
