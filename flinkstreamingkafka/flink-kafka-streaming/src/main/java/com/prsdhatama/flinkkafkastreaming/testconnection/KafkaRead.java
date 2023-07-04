package com.prsdhatama.flinkkafkastreaming.testconnection;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.api.datastream.DataStream;
import java.util.Properties;

public class KafkaRead {

    public static void main(String[] args) throws Exception {
        final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String topic = "animalia";
        String bootStrapServers = "localhost:9092";
        Properties kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", bootStrapServers);
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer <>(
                topic, new SimpleStringSchema(),
                kafkaProperties);

        DataStream<String> kafkaStream = env
                .addSource(consumer)
                .name("Kafka Source");

//        kafkaStream
//                .writeToText(params.get("output"))
//                .name("Text Output");
        kafkaStream
                .print()
                .name("Print Kafka Messages");
        env.execute("flink-read");
    }
}