package com.prsdhatama.flink.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class Deserialization<T> implements DeserializationSchema<T> {
    private final Class<T> typeClass;
    //transient biar si field mapper ga kebawa ke serialization
    public transient ObjectMapper transientMapper;

    public Deserialization(Class<T> typeClass) {
        this.typeClass = typeClass;
    }
    @Override
    public void open(InitializationContext context) throws Exception {
        // Dynamic Class Loading, au dah apaan ini
//        UserCodeClassLoader classLoader = context.getUserCodeClassLoader();
//        Class<?> objectMapperClass = classLoader.asClassLoader().loadClass("com.fasterxml.jackson.databind.ObjectMapper");
        // Instantiate ObjectMapper
        this.transientMapper = new ObjectMapper();
    }
    @Override
    public T deserialize(byte[] bytes) throws IOException{
        if (bytes == null) {
            return null;
        }else{
            try {return  transientMapper.readValue(bytes, typeClass);}
            catch (JsonProcessingException e){throw new IOException("Error deserializing object", e);}
        }
    }

    //dipake sama flink sendiri buat tau parameter selesai streamnya, di set false biar jaln trs kecuali ada keyboard intercept
    @Override
    public boolean isEndOfStream(Object o) {
        return false;
    }

    //dipake sama flink sendiri buat tau metadata class yang langi diproses
    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(typeClass);
    }
}
