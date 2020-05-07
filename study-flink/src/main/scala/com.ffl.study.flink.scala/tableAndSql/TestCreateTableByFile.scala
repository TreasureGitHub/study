package com.ffl.study.flink.scala.tableAndSql

import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.sources.CsvTableSource

/**
  * @author liufeifei  2020/05/06 11:35
  *
  */
object TestCreateTableByFile {

    def main(args: Array[String]): Unit = {
        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(streamEnv,settings)

        // 读取数据
        val tableSource = new CsvTableSource("/station.log",
            Array[String]("f1", "f2", "f3", "f4", "f5", "f6"),
            Array(Types.STRING, Types.STRING, Types.STRING, Types.STRING, Types.LONG, Types.LONG)
        )

        // 注册一张表
        tableEnv.registerTableSource("t_station_log",tableSource)

        // 可以使用table api
        // 打印表结构，或者使用Table Api，需要得到table对象
        val table: Table = tableEnv.scan("t_station_log")
        table.printSchema()

    }

}
