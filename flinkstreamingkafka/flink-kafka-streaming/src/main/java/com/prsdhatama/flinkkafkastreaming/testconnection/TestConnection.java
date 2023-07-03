package com.prsdhatama.flinkkafkastreaming.testconnection;

        import org.apache.flink.api.common.serialization.SimpleStringSchema;
        import org.apache.flink.configuration.Configuration;
        import org.apache.flink.streaming.api.datastream.DataStream;
        import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
        import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

        import java.util.Properties;

public class TestConnection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment
                .createLocalEnvironmentWithWebUI(new Configuration());

        //Set up Kafka properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test-group");

//        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("zookeeper.connect", "localhost:2181"); // Zookeeper default host:port
//        properties.setProperty("topic", "animalia");
//        properties.setProperty("group.id", "test-group");
//        properties.setProperty("auto.offset.reset", "earliest");

        // Create a Kafka consumer
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(
                "animalia",
                new SimpleStringSchema(),
                properties
        );

        // Add the Kafka consumer as a source to the Flink environment
        DataStream<String> kafkaDataStream = env.addSource(kafkaConsumer);

        // Print the Kafka messages to the console
        kafkaDataStream.print();

        // Execute the Flink job
        env.execute("Kafka Connectivity Test");
    }
}
