package com.ffl.study.flink.scala.old.tableAndSql

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/05/08 13:41
  *
  *           统计每个基站中，通话成功的通话时长
  */
object TestSQLByDurationCount {

    def main(args: Array[String]): Unit = {
        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        streamEnv.setParallelism(1)

        val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(streamEnv, settings)

        import org.apache.flink.streaming.api.scala._

        //// 读取数据
        //val tableSource = new CsvTableSource("/Users/feifeiliu/Documents/work/java/study/study-flink/src/main/resources/station.log",
        //    Array[String]("sid", "call_out", "call_in", "call_type", "callTime", "duration"),
        //    Array(Types.STRING, Types.STRING, Types.STRING, Types.STRING, Types.LONG, Types.LONG)
        //)

        // 注册一张表
        //tableEnv.registerTableSource("t_station_log", tableSource)

        //使用纯粹SQL
        //val result: Table = tableEnv.sqlQuery("select sid,sum(duration) as sum_duartion from t_station_log where call_type = 'success' group by sid")


        val stream: DataStream[StationLog] = streamEnv.readTextFile(getClass.getResource("/station.log").getPath)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })

        val table: Table = tableEnv.fromDataStream(stream)

        val result: Table = tableEnv.sqlQuery(s"select sid,sum(duration) as sum_duration from $table where callType = 'success' group by sid")

        tableEnv.toRetractStream[Row](result).filter(_._1 == true).print()

        tableEnv.execute("TestSQLByDurationCount")
    }

}
