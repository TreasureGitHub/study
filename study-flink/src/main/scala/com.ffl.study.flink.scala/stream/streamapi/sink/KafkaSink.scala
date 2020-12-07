package com.ffl.study.flink.scala.stream.streamapi.sink

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

/**
  * @author lff
  * @datetime 2020/12/05 18:34
  */
object KafkaSink {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"
        val stream: DataStream[String] = env.readTextFile(filePath).map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble).toString
        })

        stream.addSink(new FlinkKafkaProducer[String]("localhost:9092","sensor_input",new SimpleStringSchema()))

        env.execute("KafkaSink")

    }

}
