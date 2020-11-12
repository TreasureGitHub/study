package com.ffl.study.flink.scala.old.tableAndSql

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.scala.StreamTableEnvironment

/**
  * @author liufeifei  2020/05/06 11:25
  *
  */
object TestCreateTableEnvironment {

    def main(args: Array[String]): Unit = {

        // 使用Flink原生的代码创建TableEnvironment
        // 先初始化流计算上下文
        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(streamEnv,settings)
    }

}
