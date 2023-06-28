package com.prshatama.testingjackson.testjsonpropertyorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MainJsonPropertyOrderTest {

    @Test
    public void mainJsonPropertyOrder()
            throws JsonProcessingException, JsonProcessingException {

        TestingJsonPropertyOrder bean = new TestingJsonPropertyOrder(1, "My bean");


        String result = new ObjectMapper().writeValueAsString(bean);

//        assertThat(result, containsString("My bean"));
//        assertThat(result, containsString("1"));
        assertEquals("{\"id\":1,\"name\":\"My bean\"}",result);

    }


}