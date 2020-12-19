package com.ffl.study.kafka.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author lff
 * @datetime 2020/12/17 21:50
 */
public class KafkaUtils {

    private static final String LOCAL_HOST = "localhost:9092";

    /**
     * 默认生产者信息
     *
     * @return
     */
    public static Properties getDefaultProducerProperties() {
        return getDefaultProducerProperties(LOCAL_HOST);
    }

    /**
     * 默认生产者信息
     *
     * @param host
     * @return
     */
    public static Properties getDefaultProducerProperties(String host) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    /**
     * 默认消费配置信息
     *
     * @return
     */
    public static Properties getDefaultConsumerProperties() {
        return getDefaultConsumerProperties(LOCAL_HOST);
    }

    /**
     * 默认消费者配置信息
     *
     * @param host
     * @return
     */
    public static Properties getDefaultConsumerProperties(String host) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return properties;
    }
}
