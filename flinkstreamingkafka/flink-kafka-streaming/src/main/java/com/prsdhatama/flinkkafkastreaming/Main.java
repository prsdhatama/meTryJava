package com.prsdhatama.flinkkafkastreaming;

}
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParserExample {
    public static void main(String[] args) {
        try {
            // JSON data to be parsed
            String jsonData = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";

            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON data
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            // Extract values from JSON
            String name = jsonNode.get("name").asText();
            int age = jsonNode.get("age").asInt();
            String city = jsonNode.get("city").asText();

            // Display parsed values
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("City: " + city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
