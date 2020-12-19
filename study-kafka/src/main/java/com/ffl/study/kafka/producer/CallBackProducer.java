package com.ffl.study.kafka.producer;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.kafka.utils.KafkaUtils;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author lff
 * @datetime 2020/12/17 21:46
 */
public class CallBackProducer {

    private static final String TOPIC = "first";

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaUtils.getDefaultProducerProperties());

        for (int i = 0; i < 20; i++) {
            producer.send(new ProducerRecord<>(TOPIC, "CallBackProducer-" + i), new Callback() {

                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println(metadata.partition() + StringConstants.MINUS + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }

        producer.close();
    }
}
