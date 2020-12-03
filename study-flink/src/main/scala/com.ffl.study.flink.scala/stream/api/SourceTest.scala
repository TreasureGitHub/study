package com.ffl.study.flink.scala.stream.api

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
  * @author lff
  * @datetime 2020/12/02 08:33
  */
object SourceTest {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val dataList = List(
            SensorReading("sensor_1", 1547718199, 35.8),
            SensorReading("sensor_6", 1547718201, 15.4),
            SensorReading("sensor_7", 1547718202, 6.7),
            SensorReading("sensor_8", 1547718205, 38.1),
            SensorReading("sensor_9", 1547718222, 23.2),
            SensorReading("sensor_10", 1547718238, 19.9)
        )

        // 1.从集合中读取数据
        //1.1 直接从集合读
        val stream: DataStream[SensorReading] = env.fromCollection(dataList)
        stream.print()

        //1.2 从元素读
        val stream1: DataStream[SensorReading] = env.fromElements(
            SensorReading("sensor_1", 1547718199, 35.8),
            SensorReading("sensor_6", 1547718201, 15.4),
            SensorReading("sensor_7", 1547718202, 6.7),
            SensorReading("sensor_8", 1547718205, 38.1),
            SensorReading("sensor_9", 1547718222, 23.2),
            SensorReading("sensor_10", 1547718238, 19.9))

        stream1.print()


        // 2.从文件读入数据
        // 2.1 从本地文件读
        val filePath: String = PathConstants.FLINK_RES + "/wc_input"
        val stream2: DataStream[String] = env.readTextFile(filePath)
        stream2.print()


        // 2.2 从hdfs读取
        val stream3: DataStream[String] = env.readTextFile("hdfs://localhost:9000/wc/word.txt")
        stream3.flatMap(_.split(StringConstants.SPACE))
          .map((_, 1))
          .keyBy(0)
          .sum(1)
          .print()

        println("-----------------------从hdfs中读取-------------------------")

        stream3.print()


        env.execute("SourceTest")
    }

}


// 定义样例类
case class SensorReading(id: String, timestamp: Long, temperature: Double)
