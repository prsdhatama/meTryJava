package com.prshatama.testingjackson.testjsonanygetter;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;

public class TestingJsonAnyGetter {
    public String name;
    private Map<String, String> properties;


    public TestingJsonAnyGetter(String name) {
        this.name = name;
        this.properties = new HashMap<>();
    }
//    public TestingJsonAnyGetter(String name, Map<String, String> properties) {
//        this.name = name;
//        this.properties = properties;
//    }
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    public void add(String key, String value) {
        properties.put(key, value);
    }
}
