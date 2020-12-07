package com.ffl.study.flink.scala.stream.tableapi

import com.ffl.study.common.constants.PathConstants
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{DataTypes, Table}
import org.apache.flink.table.descriptors.{Csv, FileSystem, Kafka, Schema}

/**
  * @author lff
  * @datetime 2020/12/06 18:26
  */
object TableApiTest {

    def main(args: Array[String]): Unit = {
        //1.创建环境
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)

        /*
        //1.1基于老版本planner的流处理
        val settings: EnvironmentSettings = EnvironmentSettings
          .newInstance()
          .useOldPlanner()
          .inStreamingMode()
          .build()
        val oldStreamTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env,settings)


        // 1.2基于老版本的批处理
        val batchEnv: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
        val oldBatchTableEnv: BatchTableEnvironment = BatchTableEnvironment.create(batchEnv)

        // 1.3基于blink planner的流处理  新版本
        val blinkStreamSettings: EnvironmentSettings = EnvironmentSettings
          .newInstance()
          .useBlinkPlanner() // 和老版本此处有差异
          .inStreamingMode
          .build()

        val blinkStreamTableApi: StreamTableEnvironment = StreamTableEnvironment.create(env,blinkStreamSettings)


        // 1.4 基于blink planner的批处理  新版本
        val blinkBatchSettings: EnvironmentSettings = EnvironmentSettings
          .newInstance
          .useBlinkPlanner()
          .inBatchMode
          .build

        val blinkBatchTableEnv: TableEnvironment = TableEnvironment.create(blinkBatchSettings)
        */

        // 2.连接外部系统，读取数据，注册表
        // 2.1读取文件
        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"
        //val stream: DataStream[SensorReading] = env.readTextFile(filePath).map(data => {
        //    val arr: Array[String] = data.split(StringConstants.COMMA)
        //    SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        //})

        tableEnv.connect(new FileSystem().path(filePath))
          //.withFormat(new OldCsv) // 老版本
          .withFormat(new Csv) // 新版本
          .withSchema(new Schema()
          .field("id", DataTypes.STRING())
          .field("timestamp", DataTypes.BIGINT())
          .field("temperature", DataTypes.DOUBLE()))
          .createTemporaryTable("inputTable")


        // 2.2从kafka读取数据
        tableEnv.connect(new Kafka()
          .version("universal")
          .topic("sensor")
          .property("zookeeper.connect", "localhost:2181")
          .property("bootstrap.servers", "localhost:9092")
        )
          .withFormat(new Csv) // 新版本
          .withSchema(new Schema()
          .field("id", DataTypes.STRING())
          .field("timestamp", DataTypes.BIGINT())
          .field("temperature", DataTypes.DOUBLE())
        )
          .createTemporaryTable("kafkaInputTable")


        // 3查询转换
        //3.1使用table api
        val sensorTable: Table = tableEnv.from("inputTable")
        val resultTable: Table = sensorTable.select('id, 'temperature)
          .filter('id === "sensor_1")

        // 3.2 SQL
        val resultSqlTable:Table = tableEnv.sqlQuery(
            """
              |select id,temperature
              |from inputTable
              |where id = 'sensor_1'
            """.stripMargin)

        //val inputTable: Table = tableEnv.from("kafkaInputTable")
        //inputTable.toAppendStream[(String, Long, Double)].print()

        resultTable.toAppendStream[(String, Double)].print("result")
        resultSqlTable.toAppendStream[(String, Double)].print("sql")

        env.execute("TableApiTest")
    }

}
