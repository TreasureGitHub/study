package com.ffl.study.kafka.producer;

import com.ffl.study.kafka.interceptor.CountInterceptor;
import com.ffl.study.kafka.interceptor.TimeInterceptor;
import com.ffl.study.kafka.utils.KafkaUtils;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author lff
 * @datetime 2020/12/17 23:34
 */
public class InterceptorProducer {

    public static void main(String[] args) {
        Properties properties = KafkaUtils.getDefaultProducerProperties();

        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                Lists.newArrayList(TimeInterceptor.class.getName(), CountInterceptor.class.getName()));

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 20; i++) {
            producer.send(new ProducerRecord<>("first", "InterceptorProducer-" + i));
        }

        producer.close();
    }
}
