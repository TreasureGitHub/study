package com.ffl.study.flink.scala.old.transformation

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

import com.ffl.study.flink.scala.old.source.StationLog
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/20 23:10
  */
object TestRichFunctionClass {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        //env.setParallelism(1)

        import org.apache.flink.api.scala._

        val filePath = getClass.getResource("/station.log").getPath

        // 读取数据源
        val stream: DataStream[StationLog] = env.readTextFile(filePath)
          .map(
              line => {
                  val arr = line.split(",")
                  new StationLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim, arr(4).toLong, arr(5).toLong)
              }
          )

        val result: DataStream[StationLog] = stream.filter(_.callType.equals("success"))
          .map(new MyRichMapFunction)

        result.print()

        env.execute("TestFunctionClass")
    }

    class MyRichMapFunction extends RichMapFunction[StationLog,StationLog] {

        var conn:Connection = _
        var pst:PreparedStatement= _

        override def open(parameters: Configuration): Unit = {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root","root")
            pst = conn.prepareStatement("select name from t_phone where phone_number = ?")
        }

        override def close(): Unit = {
            conn.close()
            pst.close()
        }
        override def map(value: StationLog): StationLog = {

            println(getRuntimeContext.getTaskNameWithSubtasks)
            // 查询主叫号码对应姓名
            pst.setString(1,value.callOut)
            val result: ResultSet = pst.executeQuery()

            if(result.next()){
                result.getString(1)
                value.callOut = result.getString(1)
            }

            // 查询被叫号码对应姓名
            pst.setString(1,value.callIn)
            val result1: ResultSet = pst.executeQuery()

            if(result1.next()){
                result1.getString(1)
                value.callIn = result1.getString(1)
            }

            value
        }
    }

}
