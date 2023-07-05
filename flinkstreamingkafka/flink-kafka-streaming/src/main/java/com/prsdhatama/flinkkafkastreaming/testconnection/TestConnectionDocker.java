package com.prsdhatama.flinkkafkastreaming.testconnection;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

    public class TestConnectionDocker {
        public static void main(String[] args) throws Exception {
            StreamExecutionEnvironment env = StreamExecutionEnvironment
                    .createLocalEnvironmentWithWebUI(new Configuration());

            KafkaSource<String> source = KafkaSource.<String>builder()
//                    .setBootstrapServers("172.17.0.1:9092")
                    .setBootstrapServers("localhost:9094")
                    .setTopics("animalia")
                    .setGroupId("my-group")
                    .setStartingOffsets(OffsetsInitializer.earliest())
                    .setValueOnlyDeserializer(new SimpleStringSchema())
                    .build();

            DataStream<String> kafkaDataStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

            kafkaDataStream.print();

            env.execute("Kafka Connectivity Test");
        }
    }


