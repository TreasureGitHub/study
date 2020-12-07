package com.ffl.study.flink.scala.old.sink

import java.sql.{Connection, DriverManager, PreparedStatement}

import com.ffl.study.flink.scala.old.source.{MyCustomSource, StationLog}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/19 19:09
  */
object CustomJdbcSink {

    // 随机生成 stationLog 对象，然后写入mysql的表StationLong库中
    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val stream: DataStream[StationLog] = env.addSource(new MyCustomSource)

        stream.addSink(new MyCustomJdbcSink)

        env.execute("CustomJdbcSink")

    }

    // 自定义 sink类
    class MyCustomJdbcSink extends RichSinkFunction[StationLog] {

        var conn: Connection = _
        var pst: PreparedStatement = _

        // 将 station log 写入 mysql中， 每写入一条执行一次
        override def invoke(value: StationLog, context: SinkFunction.Context[_]): Unit = {
            pst.setString(1, value.sid)
            pst.setString(2, value.callOut)
            pst.setString(3, value.callIn)
            pst.setString(4, value.callType)
            pst.setLong(5, value.callTime)
            pst.setLong(6, value.duration)
            pst.executeUpdate()
        }

        //
        override def open(parameters: Configuration): Unit = {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root")
            pst = conn.prepareStatement("insert into t_station_log(sid,call_out,call_in,call_type,call_time,duration) values(?,?,?,?,?,?)")
        }

        override def close(): Unit = {
            pst.close()
            conn.close()
        }
    }

}
