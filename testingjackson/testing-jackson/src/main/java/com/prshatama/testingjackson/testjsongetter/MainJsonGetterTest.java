package com.prshatama.testingjackson.testjsongetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MainJsonGetterTest {
    @Test
    public void mainJsonGetter() throws JsonProcessingException {

        TestingJsonGetter bean = new TestingJsonGetter(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);

//        assertThat(result, containsString("My bean"));
//        assertThat(result, containsString("1"));
        assertEquals("{\"id\":1,\"name\":\"My bean\"}",result);
    }
}