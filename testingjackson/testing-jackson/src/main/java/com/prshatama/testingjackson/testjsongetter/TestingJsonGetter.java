package com.prshatama.testingjackson.testjsongetter;

import com.fasterxml.jackson.annotation.JsonGetter;

public class TestingJsonGetter {
    public int id;
    private String name; // ini juga umumnya public kalo mau bisa diambil sm serializer defaultnya tanpa pake @JsonGetter("name")

    public TestingJsonGetter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonGetter("name")
    // ini gunanya biar bisa ngasih nama custom to getter yg umumnya getName, kalo dipake ke getTheName jd bisa
    public String getTheName() {
        return name;
    }
}

