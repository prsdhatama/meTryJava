package com.prsdhatama.flinkkafkastreaming.schema;

import com.prsdhatama.flinkkafkastreaming.annotation.JacksonSerializable;
import com.fasterxml.jackson.annotation.JsonProperty;


//simple class yang provide name dan company

@JacksonSerializable
    public class SchemaDictionary {
        @JsonProperty("nama_watashi") private String name;
        @JsonProperty("company_watashi") private String company;

    public SchemaDictionary(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
