package com.ffl.study.kafka.consumer;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.kafka.utils.KafkaUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;

/**
 * @author lff
 * @datetime 2020/12/17 22:21
 */
public class MyConsumer {

    public static void main(String[] args) {
        Properties properties = KafkaUtils.getDefaultConsumerProperties();

        // 消费组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "bigdata");
        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅主题
        consumer.subscribe(Sets.newHashSet("first","third"));

        // 获取数据
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.key() + Strings.repeat(StringConstants.MINUS, 5) + consumerRecord.value());
            }
        }
    }
}
