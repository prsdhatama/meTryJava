package com.prsdhatama.flink;

import com.prsdhatama.flink.jackson.Deserialization;
import com.prsdhatama.flink.schema.SchemaCustomers;
import com.prsdhatama.flink.utils.Utils;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.Path;

import static com.prsdhatama.flink.utils.Constants.*;

public class KafkaToHDFS {
    public static void main(String[] args) throws Exception {
        ParameterTool params = Utils.parseArgs(args);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<SchemaCustomers> consumer = KafkaSource.<SchemaCustomers>builder()
                .setBootstrapServers(params.get(K_BOOTSTRAP_SERVERS))
                .setProperty("ssl.truststore.location",params.get(K_TRUSTSTORE_PATH))
                .setProperty("sasl.mechanism",params.get(K_SASL_MECHANISM))
                .setProperty("security.protocol",params.get(K_SECURITY_PROTOCOL))
                .setProperty("sasl.kerberos.service.name",params.get(SASL_KERBEROS_SERVICE_NAME))
                .setTopics(params.get(K_KAFKA_TOPIC))
                .setValueOnlyDeserializer(new Deserialization<SchemaCustomers>(SchemaCustomers.class))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setGroupId("prsdhatama-flink")
                .build();

        DataStream<SchemaCustomers> source = env.fromSource(consumer,
                WatermarkStrategy.forMonotonousTimestamps(),
                "Kafka Source").uid("kafka-source");

        // Create Path to HDFS OUTPUT
//        StreamingFileSink<String> sink = StreamingFileSink
//                .forRowFormat(new Path(params.getRequired(K_HDFS_OUTPUT)), new SimpleStringEncoder<String>("UTF-8"))
//                .build();



        source.addSink(sink)
                .name("FS Sink")
                .uid("fs-sink");
        source.print();

        env.execute("Secured Flink Streaming Job");
}
