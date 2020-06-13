package com.ffl.study.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author lff
 * @datetime 2020/06/11 21:48
 *
 * 同步发送
 */
public class SyncProducer {

    private static final String TOPIC_1 = "first";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        // 设置主机
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 设置key、value序列化
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        // 1.创建一个生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 2.调用生产者sender方法
        for (int i = 0; i < 1000; i++) {
            // 同步发送
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(TOPIC_1, String.valueOf(i), "message-" + i)).get();
            System.out.println("offset = " + recordMetadata.offset());
        }

        // 关闭生产者
        producer.close();
    }
}
