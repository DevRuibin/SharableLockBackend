package org.example.mqttserver;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static String ReadJsonFile(String path) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String changeCounter(String jsonString, String count){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse JSON into a Map
            Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);
            // Access data
            Map<String, Object> downlinkMessage = ((List<Map<String, Object>>) jsonMap.get("downlinks")).get(0);
            Map<String, Object> decodedPayload = (Map<String, Object>) downlinkMessage.get("decoded_payload");
            decodedPayload.put("counter", count);
            System.out.println(objectMapper.writeValueAsString(jsonMap));
            return objectMapper.writeValueAsString(jsonMap);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Object ReadPayload(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse JSON into a Map
            Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);

            // Access data
            Map<String, Object> uplinkMessage = (Map<String, Object>) jsonMap.get("uplink_message");
            Object payload = uplinkMessage.get("frm_payload");

            System.out.println("Field: " + payload);
            return payload;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}