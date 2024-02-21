package com.prsdhatama.flink.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prsdhatama.flink.annotation.JacksonSerializable;

//"customer_id","customer_unique_id","customer_zip_code_prefix","customer_city","customer_state"

@JacksonSerializable
public class SchemaCustomers {
    @JsonProperty private String customer_id;
    @JsonProperty private String customer_unique_id;
    @JsonProperty private String customer_zip_code_prefix;
    @JsonProperty private String customer_city;
    @JsonProperty private String customer_state;

    public SchemaCustomers() {
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_unique_id() {
        return customer_unique_id;
    }

    public String getCustomer_zip_code_prefix() {
        return customer_zip_code_prefix;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public String getCustomer_state() {
        return customer_state;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_unique_id(String customer_unique_id) {
        this.customer_unique_id = customer_unique_id;
    }

    public void setCustomer_zip_code_prefix(String customer_zip_code_prefix) {
        this.customer_zip_code_prefix = customer_zip_code_prefix;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }

    public void setCustomer_state(String customer_state) {
        this.customer_state = customer_state;
    }
}

    //default constructor

