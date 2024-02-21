package com.prsdhatama.flink;

import
import java.util.Properties;

public class KafkaConsumer {
    public static void main(String[] args) throws Exception {

//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String[] brokers = {"smtr01.ds-inovasi.com:9093", "mtr02.ds-inovasi.com:9093", "mtr03.ds-inovasi.com:9093"};
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", String.join(",", brokers)); // Use String.join() to concatenate broker addresses
        properties.setProperty("group.id", "test");
        // Only required for Kafka 0.8 (not needed for Kafka >= 0.9)
        // properties.setProperty("zookeeper.connect", "localhost:2181");

        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>("topic", new SimpleStringSchema(), properties));

        // Add your data processing logic here

        env.execute("KafkaConnector Job");
    }

    String[] brokers = {"smtr01.ds-inovasi.com:9093,mtr02.ds-inovasi.com:9093,mtr03.ds-inovasi.com:9093"};
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", brokers);
    // only required for Kafka 0.8
    properties.setProperty("zookeeper.connect", "localhost:2181");
    properties.setProperty("group.id", "test");
        DataStream<String> stream = env
                .addSource(new FlinkKafkaConsumer08<>("topic", new SimpleStringSchema(), properties));


}
