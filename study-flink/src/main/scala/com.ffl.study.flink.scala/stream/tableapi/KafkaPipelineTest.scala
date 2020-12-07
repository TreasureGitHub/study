package com.ffl.study.flink.scala.stream.tableapi

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.{DataTypes, Table}
import org.apache.flink.table.api.scala._
import org.apache.flink.table.descriptors.{Csv, Kafka, Schema}

/**
  * @author lff
  * @datetime 2020/12/06 20:53
  */
object KafkaPipelineTest {


    def main(args: Array[String]): Unit = {
        //1.创建环境
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)

        // 2.kafka输入
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
          .field("temp", DataTypes.DOUBLE())
        )
          .createTemporaryTable("kafkaInputTable")

        // 3.1简单转换
        val sensorTable: Table = tableEnv.from("kafkaInputTable")
        val resultTable: Table = sensorTable.select('id, 'temp)
          .filter('id === "sensor_1")

        // 3.2聚合转换
        val aggTable: Table = sensorTable
          .groupBy('id) // 基于ID分组
          .select('id, 'id.count as 'count)

        // 4.输出到kafka

        tableEnv.connect(new Kafka()
          .version("universal")
          .topic("sinktest")
          .property("zookeeper.connect", "localhost:2181")
          .property("bootstrap.servers", "localhost:9092")
        )
          .withFormat(new Csv) // 新版本
          .withSchema(new Schema()
          .field("id", DataTypes.STRING())
          //.field("timestamp", DataTypes.BIGINT())
          .field("temp", DataTypes.DOUBLE())
        )
          .createTemporaryTable("kafkaOutputTable")


        resultTable.insertInto("kafkaOutputTable")

        env.execute("KafkaPipelineTest")
    }

}
