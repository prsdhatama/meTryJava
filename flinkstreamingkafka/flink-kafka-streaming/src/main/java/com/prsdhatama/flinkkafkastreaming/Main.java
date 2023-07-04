package com.prsdhatama.flinkkafkastreaming;

import com.prsdhatama.flinkkafkastreaming.schema.SchemaDictionary;
import com.prsdhatama.flinkkafkastreaming.schema.SchemaPeople;
import com.prsdhatama.flinkkafkastreaming.jackson.Deserialization;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;

public class Main {

    public static final String filePath = "";

    public static void main(String[] args) throws Exception {
        //bikin env buat flink dgn tampilan webUI localhost:8080
        final StreamExecutionEnvironment env = StreamExecutionEnvironment
                .createLocalEnvironmentWithWebUI(new Configuration());
        //it written like this .<SchemaDictionary>builder() because the moethod is written like this public static <OUT> KafkaSourceBuilder<OUT> builder()
        KafkaSource<SchemaDictionary> dictionaryKafkaSource = KafkaSource
                .<SchemaDictionary>builder()
                .setBootstrapServers("localhost:9092") //localhost
                .setTopics("SchemaDictionary")
                .setValueOnlyDeserializer(new Deserialization<SchemaDictionary>(SchemaDictionary.class))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setGroupId("flink-yudi")
                .build();

        DataStream<SchemaDictionary> dictionaryDataStream = env.<SchemaDictionary>fromSource(
                dictionaryKafkaSource,
                WatermarkStrategy.forMonotonousTimestamps(),
                "dictionary-kafka"
        );

        KafkaSource<SchemaPeople> peopleKafkaSource = KafkaSource
                .<SchemaPeople>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("SchemaPeople")
                .setValueOnlyDeserializer(new Deserialization<>(SchemaPeople.class))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setGroupId("flink-yudi")
                .build();

        DataStream<SchemaPeople> peopleDataStream = env.<SchemaPeople>fromSource(
                peopleKafkaSource,
                WatermarkStrategy.forMonotonousTimestamps(),
                "people-kafka"
        );
//////////////////////debug mapper
        dictionaryDataStream.map(
                new MapFunction<SchemaDictionary, String>() {
                    @Override
                    public String map(final SchemaDictionary dictionary) throws Exception {
                        if (dictionary != null) {
                            return dictionary.getName();
                        }

                        return null;
                    }
                }
        ).print();
//////////////////////debug mapper

//        DataStream<String> socketText = env.socketTextStream("localhost", 9999);
//
//        DataStream<Dictionary> parsedDictionary = dictionary.map(
//                (MapFunction<String, Dictionary>) s -> {
//                    if (s != null) {
//                        String[] arrayString = s.split(",");
//
//                        Dictionary dict = new Dictionary();
//                        dict.setName(arrayString[0]);
//                        dict.setCompany(arrayString[1]);
//
//                        return dict;
//                    }
//
//                    return null;
//                }
//        );
//
//        DataStream<String> socketTextFiltered = socketText.filter(
//                (FilterFunction<String>) s -> {
//                    if (s != null) {
//                        return !s.toLowerCase(Locale.ROOT).contains("toto");
//                    }
//
//                    return false;
//                }
//        );
//


        ////AWAWAWWAWAW//




        KeyedStream<SchemaPeople, String> keyedPeople = peopleDataStream
                .keyBy(SchemaPeople::getName);

        KeyedStream<SchemaDictionary, String> keyedDictionary = dictionaryDataStream
                .keyBy(SchemaDictionary::getName);

        DataStream<String> joinedStream = keyedPeople.connect(keyedDictionary).process(
                new KeyedCoProcessFunction<String, SchemaPeople, SchemaDictionary, String>() {
                    private transient ValueState<SchemaDictionary> dimState;

                    @Override
                    public void open(final Configuration parameters) throws Exception {
                        super.open(parameters);

                        // Creating the state descriptor
                        final ValueStateDescriptor<SchemaDictionary> dimensionStateDescriptor =
                                new ValueStateDescriptor<>(
                                        "dictionary", SchemaDictionary.class);

                        // Initializing the ValueState
                        dimState = getRuntimeContext().getState(dimensionStateDescriptor);
                    }

                    @Override
                    public void processElement1(
                            final SchemaPeople s,
                            final KeyedCoProcessFunction<String, SchemaPeople, SchemaDictionary, String>.Context context,
                            final Collector<String> collector) throws Exception {
                        final String key = context.getCurrentKey();
                        final SchemaDictionary dict = dimState.value();

                        if (dict != null) {
                            collector.collect("Nama saya " + key + ", kerja di " + dict.getCompany());
                        }
                    }

                    @Override
                    public void processElement2(
                            final SchemaDictionary dictionary,
                            final KeyedCoProcessFunction<String, SchemaPeople, SchemaDictionary, String>.Context context,
                            final Collector<String> collector) throws Exception {
                        dimState.update(dictionary);
                    }
                }
        );

        joinedStream.print();

        env.execute("Job ga jelas");

    }
}
