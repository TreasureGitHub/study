package com.ffl.study.flink12.flinksql

import com.ffl.study.flink12.flinksql.Ddl._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
  *
  *
  * @author lff
  * @datetime 2021/05/08 23:52
  *
  *           {"name":"zhangsan","sex":"male","age":1}
  *           {"name":"zhangsan","sex":"male","age":2}
  */
object TestSQL {


    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)
        env.setParallelism(1)

        tableEnv.executeSql(CREATE_DATABASE);
        tableEnv.executeSql(SOURCE_TABLE)
        val table: Table = tableEnv.sqlQuery(INSERT_SQL)

        val value: DataStream[(Boolean, Row)] = tableEnv.toRetractStream[Row](table)
        //val properties = new Properties()
        //properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost")
        //val kafkaSink = new FlinkKafkaProducer[String]("sink_topic", new SimpleStringSchema(), properties)
        //
        //
        //JSON.toJSON()

        value.filter(item => {

            println(item)

            false
        }).print()

        env.execute()
    }
}
