package com.ffl.study.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author lff
 * @datetime 2020/12/17 20:48
 *
 * 异步发送消息
 */
public class MyProducerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建Kafka生产者配置信息
        Properties properties = new Properties();

        // 2.指定连接kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // 3.ack应答级别
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        // 4.重试次数
        properties.put("retries", 1);

        // 5.批次大小
        properties.put("batch.size", 16384);
        // 6.等待时间，默认1毫秒
        properties.put("linger.ms", 1);

        // 7.RecordAccumulator 缓冲区大小
        properties.put("buffer.memory", 33554432);

        // 8.key，value的序列类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 9.创建
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 10.发送数据
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("first", "study-kafka" + i));
            // 此处调用get即为同步发送
            // RecordMetadata first = producer.send(new ProducerRecord<>("first", "study-kafka" + i)).get();
        }

        // 11.关闭资源
        producer.close();
    }
}
