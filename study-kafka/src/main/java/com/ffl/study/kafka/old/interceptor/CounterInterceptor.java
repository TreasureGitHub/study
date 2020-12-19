package com.ffl.study.kafka.old.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author lff
 * @datetime 2020/06/11 23:33
 */
public class CounterInterceptor implements ProducerInterceptor<String, String> {

    private long successNum = 0;

    private long errorNum = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            successNum++;
        } else {
            errorNum++;
        }
    }

    @Override
    public void close() {
        System.out.println("succeed:" + successNum + "  failed" + errorNum);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
