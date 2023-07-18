package com.prsdhatama.flinkkafkastreaming.schema;


import com.prsdhatama.flinkkafkastreaming.annotation.JacksonSerializable;
import com.fasterxml.jackson.annotation.JsonProperty;

//simple class yang provide name
@JacksonSerializable
public class SchemaPeople {
    @JsonProperty private String name;

    public SchemaPeople() {
    }

    public SchemaPeople(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

// contoh json untuk people
//{
//        "name": "Wahyudi Prasidhatama"
//}