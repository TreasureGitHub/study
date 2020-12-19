package com.ffl.study.kafka.producer;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.kafka.partition.MyPartitioner;
import com.ffl.study.kafka.utils.KafkaUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author lff
 * @datetime 2020/12/17 22:11
 */
public class PartitionProducer {

    private static final String TOPIC = "first";

    public static void main(String[] args) {
        Properties properties = KafkaUtils.getDefaultProducerProperties();
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 20; i++) {
            producer.send(new ProducerRecord<>(TOPIC, "PartitionProducer-" + i),
                    ((metadata, exception) -> {
                        if (exception == null) {
                            System.out.println(metadata.partition() + StringConstants.MINUS + metadata.offset());
                        } else {
                            exception.printStackTrace();
                        }
                    })
            );
        }

        producer.close();
    }
}
