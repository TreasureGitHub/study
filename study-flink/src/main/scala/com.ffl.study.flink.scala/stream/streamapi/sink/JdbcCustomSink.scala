package com.ffl.study.flink.scala.stream.streamapi.sink

import java.sql.{Connection, DriverManager, PreparedStatement}

import com.ffl.study.flink.scala.stream.streamapi.source.{MySourceFunction, SensorReading}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala._

/**
  * @author lff
  * @datetime 2020/12/05 18:46
  */
object JdbcCustomSink {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream: DataStream[SensorReading] = env.addSource(new MySourceFunction)

        stream.addSink(new MyJdbcSinkFunc)

        env.execute("JdbcCustomSink")
    }

}

class MyJdbcSinkFunc() extends RichSinkFunction[SensorReading] {

    private var conn: Connection = _

    private var insertStmt: PreparedStatement = _

    private var updateStmt: PreparedStatement = _

    override def open(parameters: Configuration): Unit = {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "test", "test")
        insertStmt = conn.prepareStatement("insert into sensor_temp(id,temp) values (?,?)")
        updateStmt = conn.prepareStatement("update sensor_temp set temp = ? where id = ?")
    }


    override def invoke(value: SensorReading, context: SinkFunction.Context[_]): Unit = {
        updateStmt.setDouble(1, value.temperature)
        updateStmt.setString(2, value.id)
        updateStmt.execute()

        if (updateStmt.getUpdateCount == 0) {
            insertStmt.setString(1, value.id)
            insertStmt.setDouble(2, value.temperature)
            insertStmt.execute()
        }
    }

    override def close(): Unit = {
        insertStmt.close()
        updateStmt.close()
        conn.close()
    }
}
