package com.ffl.study.flink.scala.old.tableAndSql

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/05/07 14:39
  */
object TestTumbleWindowBySqlApi {

    // 每隔5秒中统计，每个基站的通话数量，假设数据时乱序。最多延迟3秒
    def main(args: Array[String]): Unit = {

        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        // 设置时间语义
        streamEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        streamEnv.setParallelism(1)

        val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(streamEnv,settings)

        import org.apache.flink.api.scala._
        import org.apache.flink.table.api.scala._

        // 读取数据源
        val stream: DataStream[StationLog] = streamEnv.socketTextStream("localhost", 8888)
          .map(line => {
              val arr = line.split(",")
              new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
          })
          .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[StationLog](Time.seconds(3)) {
              override def extractTimestamp(element: StationLog): Long = element.callTime
          })

        // 滚动窗口，窗口大小是5秒，需求：统计每5秒内，每个基站的成功通话时长总数
        // 注册一张表并制定eventtime字段
        tableEnv.registerDataStream("t_station_log",stream,'sid,'callOut,'callIn,'callType,'callTime.rowtime,'duration)

        val result: Table = tableEnv.sqlQuery("select sid,sum(duration) as duration " +
          "from t_station_log " +
          "where callType = 'success' " +
          "group by tumble(callTime,interval '5' second)" +
          ", sid")


        tableEnv.toRetractStream[Row](result)
          .filter(_._1 == true)
          .print()


        tableEnv.execute("TestWindowByTableApi")
    }

}
