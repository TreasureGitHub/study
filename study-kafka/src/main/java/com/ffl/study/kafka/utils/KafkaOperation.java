package com.ffl.study.kafka.utils;

import com.google.common.collect.Sets;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author lff
 * @datetime 2021/04/10 22:33
 */
public class KafkaOperation {

    private static final String HOST = "localhost:9092";
    private static final String TOPIC = "test";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.得到所有topic
        // System.out.println(getAllTopic(HOST));
        //
        // 2.得到所有 consumerGroup
        System.out.println(getAllConsumerGroups(HOST));

        // 3.得到topic的所有分区
        // System.out.println(getTopicPartition(HOST, "test"));

        // 4.删除topic
        // deleteTopic(HOST, TOPIC);

        // 5.创建topic
        // createTopic(HOST, "test", 10, 1);

        // 6.得到分区的最初offset
        // System.out.println(getTopicStartOffset(HOST, TOPIC));

        // 7.得到分区的当前offset
        System.out.println(getTopicEndOffset(HOST,TOPIC));

        // System.out.println(getTopicPartition(HOST,TOPIC));


        // 2.获取topic的partition
        // ListConsumerGroupsResult consumerGroupsResult = adminClient.listConsumerGroups();
        // for (ConsumerGroupListing consumerGroupListing : consumerGroupsResult.all().get()) {
        //     System.out.println(consumerGroupListing);
        // }

        // // 消费组
        // properties.put(ConsumerConfig.GROUP_ID_CONFIG, "bigdata");
        // // 设置手动提交
        // properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // // 创建消费者
        // properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // List<PartitionInfo> partitionInfoList = consumer.partitionsFor("test123");
        //
        //
        // System.out.println(partitionInfoList);
    }


    // public static List<PartitionInfo> getPartitionInfo(String host, String topic) throws ExecutionException, InterruptedException {
    //     if (!hasTopic(host, topic)) {
    //         throw new IllegalArgumentException(String.format("has no this topic : %s", topic));
    //     }
    //     AdminClient adminClient = getAdminClient(host);
    //     ListConsumerGroupsResult consumerGroupsResult = adminClient.listConsumerGroups();
    //     Collection<ConsumerGroupListing> consumerGroupListings = consumerGroupsResult.all().get();
    //     for (ConsumerGroupListing consumerGroupListing : consumerGroupListings) {
    //         consumerGroupListing.isSimpleConsumerGroup();
    //     }
    //
    //     adminClient.close();
    //
    //     return null;
    // }

    /**
     * 得到所有的 consumer group
     *
     * @param host
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Collection<ConsumerGroupListing> getAllConsumerGroups(String host) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(host);
        ListConsumerGroupsResult consumerGroupsResult = adminClient.listConsumerGroups();
        Collection<ConsumerGroupListing> consumerGroupListings = consumerGroupsResult.all().get();
        adminClient.close();
        return consumerGroupListings;
    }

    /**
     * 删除topic
     *
     * @param host
     * @param topicName
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void deleteTopic(String host, String topicName) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(host);
        if (!hasTopic(host, topicName)) {
            System.out.println(String.format("has no topic %s ！", topicName));
            adminClient.close();
            return;
        }

        adminClient.deleteTopics(Collections.singleton(topicName));
        adminClient.close();
    }

    public static boolean createTopic(String host, String topic, int partitionNum, int replicationFactor) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(host);
        NewTopic newTopic = new NewTopic(topic, partitionNum, (short) replicationFactor);
        CreateTopicsResult topicsResult = adminClient.createTopics(Sets.newHashSet(newTopic));
        boolean done = topicsResult.all().isDone();
        adminClient.close();
        return done;
    }

    /**
     * 得到topic对应的partition
     *
     * @param host
     * @param topic
     * @return
     */
    public static List<PartitionInfo> getTopicPartition(String host, String topic) {
        KafkaConsumer<String, Object> kafkaConsumer = new KafkaConsumer<>(getKafkaProperty(host));
        List<PartitionInfo> partitionInfoList = kafkaConsumer.partitionsFor(topic);
        kafkaConsumer.close();
        return partitionInfoList;
    }

    /**
     * 是否存在 topic
     *
     * @param host
     * @param topicName
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static boolean hasTopic(String host, String topicName) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(host);
        ListTopicsResult topicsResult = adminClient.listTopics();
        for (String name : topicsResult.names().get()) {
            if (name.equals(topicName)) {
                adminClient.close();
                return true;
            }
        }

        adminClient.close();
        return false;
    }

    /**
     * 得到 topic的初始topic
     *
     * @param host
     * @param topic
     * @return
     */
    public static Map<TopicPartition, Long> getTopicStartOffset(String host, String topic) {
        KafkaConsumer<String, Object> kafkaConsumer = new KafkaConsumer<>(getKafkaProperty(host));
        List<PartitionInfo> partitionInfoList = kafkaConsumer.partitionsFor(topic);
        Set<TopicPartition> topicPartitions = partitionInfoList.stream().map(item -> new TopicPartition(item.topic(), item.partition())).collect(Collectors.toSet());
        Map<TopicPartition, Long> topicPartitionLongMap = kafkaConsumer.beginningOffsets(topicPartitions);
        kafkaConsumer.close();
        return topicPartitionLongMap;
    }

    /**
     * 得到 topic的当前offset
     *
     * @param host
     * @param topic
     * @return
     */
    public static Map<TopicPartition, Long> getTopicEndOffset(String host, String topic) {
        KafkaConsumer<String, Object> kafkaConsumer = new KafkaConsumer<>(getKafkaProperty(host));
        List<PartitionInfo> partitionInfoList = kafkaConsumer.partitionsFor(topic);
        Set<TopicPartition> topicPartitions = partitionInfoList.stream().map(item -> new TopicPartition(item.topic(), item.partition())).collect(Collectors.toSet());

        Map<TopicPartition, Long> topicPartitionLongMap = kafkaConsumer.endOffsets(topicPartitions);
        kafkaConsumer.close();
        return topicPartitionLongMap;
    }


    /**
     * 得到所有的topic
     *
     * @param host
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Map<String, TopicListing> getAllTopic(String host) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(host);
        KafkaFuture<Map<String, TopicListing>> mapKafkaFuture = adminClient.listTopics().namesToListings();
        return mapKafkaFuture.get();
    }


    /**
     * 得到管理对象
     *
     * @param host
     * @return
     */
    public static AdminClient getAdminClient(String host) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        return AdminClient.create(properties);
    }

    // 获取某个Topic的所有分区以及分区最新的Offset
    // public static void getPartitionsForTopic() {

    // Collection<PartitionInfo> partitionInfos = consumer.partitionsFor("test");
    // System.out.println("Get the partition info as below:");
    // List<TopicPartition> tp =new ArrayList<TopicPartition>();
    // partitionInfos.forEach(str -> {
    //     System.out.println("Partition Info:");
    //     System.out.println(str);
    //
    //     tp.add(new TopicPartition(TOPIC,str.partition()));
    //     consumer.assign(tp);
    //     consumer.seekToEnd(tp);
    //
    //     System.out.println("Partition " + str.partition() + " 's latest offset is '" + consumer.position(new TopicPartition(TOPIC, str.partition())));
    // });
    // }

    /**
     * 得到kafka 的默认 Properties
     *
     * @param host
     * @return
     */
    public static Properties getKafkaProperty(String host) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return properties;
    }

}
