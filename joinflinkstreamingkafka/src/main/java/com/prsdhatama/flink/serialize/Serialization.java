package com.prsdhatama.flink.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prsdhatama.flink.schema.OrdersAndCustomers;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Serialization<T> implements KafkaRecordSerializationSchema<T> {

    private String topic;
    private final Class<T> typeClass;
    public transient ObjectMapper transientMapper;

    public Serialization(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public void open(SerializationSchema.InitializationContext context, KafkaSinkContext sinkContext) throws Exception {

        KafkaRecordSerializationSchema.super.open(context, sinkContext);
        this.transientMapper = new ObjectMapper();
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(T element, KafkaSinkContext context, Long timestamp) {
        if (element == null) {
            return null;
        }else{
            try {
            // Serialize OrdersAndCustomers object to byte array
//                String jsonString = ((OrdersAndCustomers) element).toJoinedJsonString();
                // Serialize the JSON string to byte array
//                byte[] serializedValue = jsonString.getBytes(StandardCharsets.UTF_8);
                // Convert OrdersAndCustomers object to JSON ObjectNode
            ObjectNode jsonNode = ((OrdersAndCustomers) element).toJoinedJsonString();
            byte[] serializedValue = transientMapper.writeValueAsBytes(jsonNode);
            // Create ProducerRecord with byte array representations of data and optional timestamp
            return new ProducerRecord<>(
                    "prsdhatama_olist_sink",
                    null,
//                    System.currentTimeMillis(), timestamp
                    null,
                    null,
                    serializedValue);
        } catch (JsonProcessingException e) {throw new RuntimeException("Error serializing OrdersAndCustomers object to JSON", e);}
        }
    }

//    @Override
//    public ProducerRecord<String,T> serialize(T element, KafkaSinkContext sinkContext, @Nullable Long timestamp) {
//        // Serialize the object to JSON using Jackson
//        String jsonString = element.toJoinedJsonString();
////        private final ObjectMapper objectMapper;
//        byte[] serializedValue = null;
//
//        try {
//            // Serialize OrdersAndCustomers object to byte array
//            serializedValue = transientMapper.writeValueAsBytes(element);
//            // Create ProducerRecord with byte array representations of data and optional timestamp
//            return new ProducerRecord<>("prsdhatama_olist_sink", serializedValue, timestamp);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error serializing OrdersAndCustomers object to JSON", e);
//        }
////        return new ProducerRecord<>("prsdhatama_olist_sink", serializedValue);
//    }
}
