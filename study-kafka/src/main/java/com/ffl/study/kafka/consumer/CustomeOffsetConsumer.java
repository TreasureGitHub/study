package com.ffl.study.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * @author lff
 * @datetime 2020/06/11 22:45
 */
public class CustomeOffsetConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "1205");


        // 1. 创建 消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("first"), new ConsumerRebalanceListener() {

            // 提交当前负责的offset
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("===================回收的分区==================");
                for(TopicPartition partition:partitions){
                    System.out.println("partition = " + partition);
                }
            }

            // 定位新分配的分区offset
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("===================重新得到的分区==================");
                for(TopicPartition partition:partitions){
                    System.out.println("partition = " + partition);
                }

                for (TopicPartition partition : partitions) {
                    Long offset = getPartitionOffset(partition);
                    consumer.seek(partition,offset);
                }
            }
        });

        // 2.调用 poll
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("topic = " + record.topic() + "offset = " + record.offset() + " value = " + record.value());

                TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                commitOffset(topicPartition,record.offset() + 1);
            }
        }
    }

    private static void commitOffset(TopicPartition topicPartition, long l) {
    }

    private static Long getPartitionOffset(TopicPartition partition){
        return null;
    }
}
