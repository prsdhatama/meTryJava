package com.prshatama.testingjackson.testjsongetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainJsonGetter {
    @Test
    public void MainJsonGetter() throws JsonProcessingException, JsonProcessingException {

        TestingJsonGetter bean = new TestingJsonGetter(1, "My bean");

        String result = null;
        try {
            result = new ObjectMapper().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }
}