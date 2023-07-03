package com.prsdhatama.flinkkafkastreaming.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class Deserialization<T> implements DeserializationSchema<T> {
    //bikin field private final namanya type, Class<T> is a default way to say this is a class with a type parameter
    private final Class<T> typeClass;
    //transient biar si field mapper ga kebawa ke serialization
    public transient ObjectMapper transientMapper;

    //bikin constructor
    public Deserialization(Class<T> type) {
        this.typeClass = type;
    }

    @Override
    public void open(InitializationContext context) throws Exception {
        //ngaktifin open dari super class (DeserializationSchema)
        DeserializationSchema.super.open(context);
        //bikin mapper instance dari class yg kita buat
        this.transientMapper = new ObjectMapper();
    }

    @Override
    public T deserialize(byte[] bytes) throws IOException {
        if (bytes != null) {
            try {
                //mencoba untuk memanggil method readValue, dmn ini merupakan proses deserialisasinya dgn objeknya adalah bytes menjadi typeClass
                return transientMapper.readValue(bytes, typeClass);
            }
            //menangkap exception yg dilempar oleh IOException
            catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    //2 method dibawah ini digunain buat fine-tuning di kafkaSource method,
    //isEndOfStream buat nandain apakah streamnya udah selesai atau belum yg mana disini parameter yg dikasih adalah type parameter
    //getProducedType ini juga diperluin sama kafkaSource
    @Override
    public boolean isEndOfStream(T t) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(typeClass);
    }
}

