package com.ffl.study.flink.scala.old.tableAndSql

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, Table, Tumble}
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/05/07 14:39
  */
object TestWindowByTableApi {

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

        // 从datastream中动态创建table
        val table:Table = tableEnv.fromDataStream(stream,'sid,'callOut,'callIn,'callType,'callTime.rowtime,'duration)

        // 开窗，滚动窗口 第一种写法
        //table.window(Tumble.over("5.second").on('callTime).as("window"))
        // 第二种写法
        val result: Table = table.window(Tumble over 5.second on 'callTime as 'window)
          .groupBy('window, 'sid)  // 必须使用两个字段分组，分别是窗口和基站id
          .select('sid, 'window.start, 'window.end, 'sid.count)   // 聚合计算

        tableEnv.toRetractStream[Row](result)
          .filter(_._1 == true)
          .print()


        tableEnv.execute("TestWindowByTableApi")

        // 如果是滑动窗口
        //table.window(Slide over 10.second every 5.second on 'callTime as 'window)
        //table.window(Slide.over("10.sencond").every("5.second").on("callTime").as("window"))

    }

}
