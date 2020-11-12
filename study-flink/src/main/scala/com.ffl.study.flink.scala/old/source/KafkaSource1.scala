package com.ffl.study.flink.scala.old.source

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

/**
  * @author lff
  * @datetime 2020/04/18 21:33
  *
  * kafka中数据为普通字符串
  */
object KafkaSource1 {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        // 连接kfka
        val props = new Properties()
        props.setProperty("bootstrap.servers","hadoop101:9092,hadoop102:9092,hadoop103:9092")
        props.setProperty("group.id","flink01")
        props.setProperty("key.deserializer",classOf[StringDeserializer].getName)
        props.setProperty("value.deserializer",classOf[StringDeserializer].getName)
        props.setProperty("auto.offset.reset","latest")

        val source: DataStream[String] = env.addSource(new FlinkKafkaConsumer[String]("topic",new SimpleStringSchema(),props))

        source.print()

        env.execute("KafkaSource1")
    }

}
