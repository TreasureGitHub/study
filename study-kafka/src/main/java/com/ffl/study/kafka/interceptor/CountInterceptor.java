package com.ffl.study.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author lff
 * @datetime 2020/12/17 23:31
 */
public class CountInterceptor implements ProducerInterceptor {

    private int success = 0;

    private int fail = 0;

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (metadata != null) {
            success++;
        } else {
            fail++;
        }
    }

    @Override
    public void close() {
        System.out.println("success:" + success);
        System.out.println("fail:" + fail);
    }
}
