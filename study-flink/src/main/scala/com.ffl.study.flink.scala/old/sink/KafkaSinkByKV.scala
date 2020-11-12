package com.ffl.study.flink.scala.old.sink

import java.lang
import java.util.Properties

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaProducer, KafkaSerializationSchema}
import org.apache.kafka.clients.producer.ProducerRecord

/**
  * @author lff
  * @datetime 2020/04/19 16:57
  */
object KafkaSinkByKV {

    // 将netcat作为数据源，统计每个单词的数量，并且将统计的结果写入kfaka
    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        val stream: DataStream[String] = env.socketTextStream("localhost",8888)

        val result: DataStream[(String, Int)] = stream
          .flatMap(_.split(" "))
          .map((_, 1))
          .keyBy(0)
          .sum(1)

        // 创建连接kafka的属性
        val props = new Properties()
        props.setProperty("bootstrap.servers","localhost")

        val kafkaSink = new FlinkKafkaProducer[(String, Int)](
            "topic3",
            new KafkaSerializationSchema[(String, Int)] { // 自定义的匿名内部类

                override def serialize(element: (String, Int), timestamp: lang.Long): ProducerRecord[Array[Byte], Array[Byte]] = {
                    new ProducerRecord("topic", element._1.getBytes, (element._2 + "").getBytes)
                }
            },
            props,
            FlinkKafkaProducer.Semantic.EXACTLY_ONCE
        )

        result.addSink(kafkaSink)

        env.execute("KafkaSinkByKV")
    }

}
