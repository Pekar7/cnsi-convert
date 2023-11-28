package com.example.dbspring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {

    public static String swapFields(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            if (jsonNode.isObject()) {
                ObjectNode swappedNode = objectMapper.createObjectNode();

                jsonNode.fields().forEachRemaining(entry -> {
                    swappedNode.put(entry.getValue().asText(), entry.getKey());
                });

                return swappedNode.toString();
            } else {
                return jsonString; // если это не объект JSON, возвращаем исходную строку
            }
        } catch (Exception e) {
            e.printStackTrace();
            return jsonString; // в случае ошибки возвращаем исходную строку
        }
    }

    public static void main(String[] args) {
        String inputJson = "";
        String outputJson = swapFields(inputJson);
        System.out.println(outputJson);
    }
}

/*
этот коод делает "{ \"hello\":\"string\", \"231313\":\"integer\", \"d\":\"char\" }"
{"string":"hello","integer":"231313","char":"d"}

*/