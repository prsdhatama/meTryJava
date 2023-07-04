package com.prsdhatama.flinkkafkastreaming.testconnection;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

public class TestConnection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment
                .createLocalEnvironmentWithWebUI(new Configuration());

        //Set up Kafka properties
//        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("group.id", "test-group");

//        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("zookeeper.connect", "localhost:2181"); // Zookeeper default host:port
//        properties.setProperty("topic", "animalia");
//        properties.setProperty("group.id", "test-group");
//        properties.setProperty("auto.offset.reset", "earliest");
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("quickstart-events")
                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();


//        KafkaSource<String> kafkaSource = KafkaSource
//                .<String>builder()
//                .setBootstrapServers("localhost:9092")
//                .setTopics("animalia")
//                .setDeserializer(new SimpleStringSchema())
//                .setProperties(properties)
//                .build();


        // Create a Kafka consumer
//        KafkaSource<SchemaDictionary> dictionaryKafkaSource = KafkaSource
//                .<SchemaDictionary>builder()
//                .setBootstrapServers("localhost:9092") //localhost
//                .setTopics("SchemaDictionary")
//                .setValueOnlyDeserializer(new Deserialization<SchemaDictionary>(SchemaDictionary.class))
//                .setStartingOffsets(OffsetsInitializer.earliest())
//                .setGroupId("flink-yudi")
//                .build();


//        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(
//                "animalia",
//                new SimpleStringSchema(),
//                properties
//        );
        DataStream<String> kafkaDataStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        // Add the Kafka consumer as a source to the Flink environment
//        DataStream<String> kafkaDataStream = env.addSource(kafkaConsumer);

        // Print the Kafka messages to the console
//        kafkaDataStream.print();
        kafkaDataStream.print();

        // Execute the Flink job
        env.execute("Kafka Connectivity Test");
    }
}
