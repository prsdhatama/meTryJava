package com.prsdhatama.flink;

import org.apache.flink.connector.kafka.source.KafkaSource;

public class KafkaConnector {

    String brokers = "smtr01.ds-inovasi.com:9093,mtr02.ds-inovasi.com:9093,mtr03.ds-inovasi.com:9093";
    KafkaSource<String> source = KafkaSource.<String>builder()
            .setBootstrapServers(brokers)
            .setTopics("input-topic")
            .setGroupId("my-group")
            .setStartingOffsets(OffsetsInitializer.earliest())
            .setValueOnlyDeserializer(new SimpleStringSchema())
            .build();



env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

}
