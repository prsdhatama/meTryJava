package com.prshatama.testingjackson.testjsonanygetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

//public class MainJsonAnyGetter {

public class MainJsonAnyGetterTest {
    @Test
    public void mainJsonAnyGetter() throws JsonProcessingException {
//        @Test
//        public void whenSerializingUsingJsonAnyGetter_thenCorrect ()
//        String[] myBean = {"value1", "value2", "value3"};
        TestingJsonAnyGetter bean = new TestingJsonAnyGetter("My Bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");

        String result = new ObjectMapper().writeValueAsString(bean);

        assertThat(result, containsString("attr1"));
        assertThat(result, containsString("val2"));

        //cannot use the below code because jackson serialize it in random order of key
//        assertEquals("{\"name\":\"My Bean\"," +
//                "\"attr1\":\"val1\" +" +
//                "\"attr2\":\"val2\"}",result);

    }

}
