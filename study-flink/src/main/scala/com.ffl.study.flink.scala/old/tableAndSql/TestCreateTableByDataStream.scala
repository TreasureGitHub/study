package com.ffl.study.flink.scala.old.tableAndSql

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.types.Row
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.scala._

/**
  * @author liufeifei  2020/05/06 11:35
  *
  */
object TestCreateTableByDataStream {

    def main(args: Array[String]): Unit = {
        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(streamEnv, settings)



        val stream: DataStream[StationLog] = streamEnv.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        // 修改字段名称
        // val table: Table = tableEnv.fromDataStream(stream, 'id, 'call_out, 'call_in) // 把后面的字段补全
        // table.printSchema()

        val table: Table = tableEnv.fromDataStream(stream)

        // filter过滤
        //val result: Table = table.filter('callType === "success")
        //val ds: DataStream[Row] = tableEnv.toAppendStream[Row](result)
        //ds.print()

        // 分组聚合
        val result: Table = table.groupBy('sid).select('sid,'sid.count as 'log_cnt)

        tableEnv.toRetractStream[Row](result).filter(_._1 == true).print() // 装填中新增的数据加了false标签

        streamEnv.execute()
    }

}
