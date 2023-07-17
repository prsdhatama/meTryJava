package com.prsdhatama.feedinghivetokafka.dev;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.*;
import java.util.Properties;

public class TestConnection {
    private static final String BOOTSTRAP_SERVERS = "localhost:9094";
    private static final String TOPIC = "your_topic_name";
    private static final String HIVE_CONNECTION_URL = "jdbc:hive2://localhost:10000/default";
    private static final String HIVE_USERNAME = "prsdhatama";
    private static final String HIVE_PASSWORD = "prsdhatama";

    public static void main(String[] args) {
        // Connect to Kafka producer
        Producer<String, String> producer = createKafkaProducer();

        // Produce messages to Kafka
        produceMessages(producer);

        // Connect to Hive and execute a query
        executeHiveQuery();
    }

    private static Producer<String, String> createKafkaProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(properties);
    }

    private static void produceMessages(Producer<String, String> producer) {
        for (int i = 1; i <= 5; i++) {
            String message = "Message " + i;
            producer.send(new ProducerRecord<>(TOPIC, message), (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("Produced message: " + message);
                } else {
                    System.err.println("Failed to produce message: " + message);
                    exception.printStackTrace();
                }
            });
        }
        producer.flush();
        producer.close();
    }

    private static void executeHiveQuery() {
        try (Connection connection = DriverManager.getConnection(HIVE_CONNECTION_URL, HIVE_USERNAME, HIVE_PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM your_table_name";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Process the result set
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String company = resultSet.getString("company");
                System.out.println("Name: " + name + ", Age: " + age + ", Company: " + company);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
