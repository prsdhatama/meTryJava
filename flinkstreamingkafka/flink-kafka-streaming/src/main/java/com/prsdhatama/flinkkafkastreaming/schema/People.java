package com.prsdhatama.flinkkafkastreaming.schema;


import com.ds_inovasi.flink.annotation.JacksonSerializable;
import com.fasterxml.jackson.annotation.JsonProperty;

@JacksonSerializable
public class People {
    @JsonProperty  private String name;

    public People() {
    }

    public People(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
