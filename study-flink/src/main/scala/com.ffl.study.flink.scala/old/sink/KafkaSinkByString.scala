package com.ffl.study.flink.scala.old.sink

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

/**
  * @author lff
  * @datetime 2020/04/19 16:44
  */
object KafkaSinkByString {

    // kafka 作为Sink的第一种情况：String
    // 将netcat中的每一个单词写入kafka
    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[String] = env.socketTextStream("localhost",8888)

        val words: DataStream[String] = stream.flatMap(_.split(" "))

        words.addSink(new FlinkKafkaProducer[String]("localhost","topic3",new SimpleStringSchema()))

        env.execute("KafkaSinkByString")
    }

}
