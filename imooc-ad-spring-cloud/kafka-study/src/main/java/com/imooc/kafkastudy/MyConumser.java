package com.imooc.kafkastudy;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;


public class MyConumser {

    private static KafkaConsumer<String, String> consumer;
    private static Properties properties;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "kafkaStudy");

    }


    private static void generalConsumerMessageAutoCommit() {
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("imooc_ad_test_x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));
                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }


    private static void generalConsumerMessageSyncCommit() {
        properties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("imooc_ad_test_x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));

                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }

                try {
                    consumer.commitSync();
                } catch (CommitFailedException e) {
                    System.out.println("commit failed error:" + e.getMessage());
                }

                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }


    private static void generalConsumerMessageASyncCommit() {
        properties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("imooc_ad_test_x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));

                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }

                consumer.commitAsync();

                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }


    private static void generalConsumerMessageASyncCommitWithCallback() {
        properties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("imooc_ad_test_x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));

                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }

                consumer.commitAsync((offsets, exception) -> {
                    if (exception != null) {
                        System.out.println("offsets:" + offsets);
                        System.out.println("commit failed for offsets:" + exception.getMessage());
                    }

                });

                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }


    private static void mixSyncAndAsyncCommit() {
        properties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("imooc_ad_test_x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));
                }

                consumer.commitAsync();
            }
        } catch (Exception e) {
            System.out.println("commit async error:" + e.getMessage());
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {

        mixSyncAndAsyncCommit();
    }
}
