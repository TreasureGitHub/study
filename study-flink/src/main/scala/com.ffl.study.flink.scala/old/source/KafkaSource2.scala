package com.ffl.study.flink.scala.old.source

import java.util.Properties

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, KafkaDeserializationSchema}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer

/**
  * @author lff
  * @datetime 2020/04/18 21:33
  *
  *           kafka中数据为普通字符串
  */
object KafkaSource2 {


    val ENCODE = "utf-8"

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        // 连接kfka
        val props = new Properties()
        // 节点
        props.setProperty("bootstrap.servers", "hadoop101:9092,hadoop102:9092,hadoop103:9092")
        // 消费组
        props.setProperty("group.id", "flink02")

        props.setProperty("key.deserializer", classOf[StringDeserializer].getName)
        props.setProperty("value.deserializer", classOf[StringDeserializer].getName)
        props.setProperty("auto.offset.reset", "latest")

        val source: DataStream[(String, String)] = env.addSource(new FlinkKafkaConsumer[(String, String)]("topic2",new MyKafkaReader,props))

        source.print()

        env.execute("KafkaSource1")
    }


    // 自定义类从kfaka中读取键值对的数据

    class MyKafkaReader extends KafkaDeserializationSchema[(String, String)] {


        // 判断流是否结束
        override def isEndOfStream(t: (String, String)): Boolean = false

        // 反序列化
        override def deserialize(consumerRecord: ConsumerRecord[Array[Byte], Array[Byte]]): (String, String) = {
            if (consumerRecord != null) {
                var key:String = null
                var value:String = null

                if(consumerRecord.key() != null){
                    key = new String(consumerRecord.key(),"utf-8")
                }

                if(consumerRecord.value() != null){
                    value = new String(consumerRecord.value(),"utf-8")
                }

                (key,value)
            } else {
                ("null", "null")
            }

        }

        // 指定类型
        override def getProducedType: TypeInformation[(String, String)] = {
            createTuple2TypeInformation(createTypeInformation[String],createTypeInformation[String])
        }
    }

}
