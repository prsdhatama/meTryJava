package com.prsdhatama.feedinghivetokafka.dev;

import org.apache.hive.jdbc.HiveConnection;
import org.apache.hive.jdbc.HiveDriver;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

public class ConsumeKafkaHive {
    private static final String kafkaBootstrapServers = "localhost:9094";
    private static final String kafkaTopic = "OfficeEmployee";
    private static final String kafkaGroupId = "hive-yudi";
    private static final String hiveHost = "localhost";
    private static final int hivePort = 10000;
    private static final String hiveUsername = "prsdhatama";
    private static final String hivePassword = "prsdhatama";
    private static final String hiveDatabase = "default";
    private static final String hiveTable = "office_employee";

    public static void main(String[] args) {
        // Set Kafka consumer properties
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create Kafka Authentication usr and password
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user", hiveUsername);
        connectionProps.setProperty("password", hivePassword);

        // Create Kafka consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);

        // Subscribe to Kafka topic
        consumer.subscribe(Collections.singletonList(kafkaTopic));

        // Set Hive connection properties
        String connectionUrl = "jdbc:hive2://" + hiveHost + ":" + hivePort + "/" + hiveDatabase;



        // Set Config if using authorization
//        Configuration conf = new Configuration();
//        conf.set("hadoop.security.authentication", "Kerberos");
//        conf.set("hadoop.security.authorization", "true");

        // Authenticate with Kerberos if necessary
//        try {
//            UserGroupInformation.setConfiguration(conf);
//            UserGroupInformation.loginUserFromKeytab("principal", "keytab_path");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }

        // Create Hive connection
        try {
            HiveDriver driver = new HiveDriver();
            HiveConnection connection = (HiveConnection) driver.connect(connectionUrl,  connectionProps);
//            connection.authenticate(hiveUsername, hivePassword);
            connection.setSchema(hiveDatabase);

            // Consume messages from Kafka and insert into Hive table
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                records.forEach(record -> {
                    String value = record.value();
                    System.out.println("Received message: " + value);

                    // Insert the message into Hive table
                    String query = String.format("INSERT INTO %s (name, age, company) VALUES (%s)", hiveTable, value);
                    try {
//                        System.out.println(query);
                        connection.createStatement().execute(query);
//                        connection.commit(); // for transactional
                        System.out.println("Inserted message into Hive: " + value);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
//            example of data in kafka
//
//            "Elvin", 25, "PT DSI"
//