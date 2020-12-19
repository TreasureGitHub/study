package com.ffl.study.kafka.interceptor;

import com.ffl.study.common.constants.StringConstants;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author lff
 * @datetime 2020/12/17 23:27
 */
public class TimerInterceptor implements ProducerInterceptor<String, String> {

    @Override
    public void configure(Map<String, ?> configs) {

    }

    /**
     * 在send之前
     *
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        // 1.取出数据
        String value = record.value();

        // 2.创建对象并返回
        return new ProducerRecord<String, String>(record.topic(), record.partition(), record.key(), System.currentTimeMillis() + StringConstants.MINUS + value);
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }
}
