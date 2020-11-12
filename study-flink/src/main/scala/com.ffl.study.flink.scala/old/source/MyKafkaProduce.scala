package com.ffl.study.flink.scala.old.source

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.util.Random

/**
  * @author lff
  * @datetime 2020/04/18 22:05
  */
object MyKafkaProduce {

    def main(args: Array[String]): Unit = {

        val props = new Properties()
        // 节点
        props.setProperty("bootstrap.servers", "hadoop101:9092,hadoop102:9092,hadoop103:9092")

        props.setProperty("key.deserializer", classOf[StringDeserializer].getName)
        props.setProperty("value.deserializer", classOf[StringDeserializer].getName)

        // 创建producer
        var producer = new KafkaProducer[String, String](props)

        val random = new Random()

        // 死循环生产键值对的数据
        while (true) {
            val data = new ProducerRecord[String, String]("topic2", "key" + random.nextInt(10), "value" + random.nextInt(100))
            producer.send(data)
            Thread.sleep(1000)
        }

        producer.close()

    }

}
