package com.prshatama.testingjackson.testjsonpropertyorder;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id","name" }) // ngatur urutan serialisasinya
public class TestingJsonPropertyOrder {
    public int id;
    public String name;

    public TestingJsonPropertyOrder(int id, String name) {
        this.id = id;
        this.name = name;
    }


}