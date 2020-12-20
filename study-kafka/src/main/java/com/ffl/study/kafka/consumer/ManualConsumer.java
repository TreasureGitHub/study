package com.ffl.study.kafka.consumer;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.kafka.utils.KafkaUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;

/**
 * @author lff
 * @datetime 2020/12/17 22:21
 * <p>
 * 手动提交
 */
public class ManualConsumer {

    public static void main(String[] args) {
        Properties properties = KafkaUtils.getDefaultConsumerProperties();

        // 消费组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "bigdata");

        // 设置手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // 重置消费者的offset: 1.换组；2.offset保留过期  如果没过期：则不会重新消费
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅主题
        consumer.subscribe(Sets.newHashSet("first", "third"));

        // 获取数据
        while (true) {
            // Duration.ofMillis(100) 为过期时间，如果过期时间内有数据返回数据，否则返回空记录
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.key() + Strings.repeat(StringConstants.MINUS, 5) + consumerRecord.value());
            }

            //异步提交
            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                    if (exception != null) {
                        System.err.println("Commit  failed for" + offsets);
                    }
                }
            });
        }
    }
}
