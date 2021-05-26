package com.ffl.study.flink12.flinksql

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.table.api.Expressions.$


/**
  * @author lff
  * @datetime 2021/05/26 22:04
  */
object HbaseTest {

    val CREATE_TABLE: String =
        s"""
           |
           |create table test  (
           | rowkey     string,
           | c          row<col1 string>,
           | primary key (rowkey) not enforced
           |) with (
           | 'connector' = 'hbase-2.2',
           | 'table-name' = 'mytable',
           | 'zookeeper.quorum' = 'localhost:2181'
           |)
         """.stripMargin

    val SELECT_SQL:String =
        s"""
           |insert into test
           |select col rowkey
           |     , row(col)
           |  from data
         """.stripMargin

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)
        env.setParallelism(1)

        val ds: DataStream[String] = env.socketTextStream("localhost",7777)

        tableEnv.createTemporaryView("data",ds,$("col"))


        //tableEnv.sqlQuery(SELECT_SQL).execute().print()
        tableEnv.executeSql(CREATE_TABLE)
        tableEnv.executeSql(SELECT_SQL)
    }

}
