package com.example.dbspring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main2 {

    public static String swapFields(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            if (jsonNode.isObject()) {
                ObjectNode swappedNode = objectMapper.createObjectNode();

                jsonNode.fields().forEachRemaining(entry -> {
                    if (entry.getKey().equals("records") && entry.getValue().isArray()) {
                        ArrayNode originalRecords = (ArrayNode) entry.getValue();
                        ArrayNode swappedRecords = objectMapper.createArrayNode();

                        originalRecords.elements().forEachRemaining(record -> {
                            if (record.isObject()) {
                                ObjectNode swappedRecord = objectMapper.createObjectNode();

                                record.fields().forEachRemaining(recordEntry -> {
                                    if (recordEntry.getKey().equals("fields") && recordEntry.getValue().isArray()) {
                                        ArrayNode originalFields = (ArrayNode) recordEntry.getValue();
                                        ArrayNode swappedFields = objectMapper.createArrayNode();

                                        originalFields.elements().forEachRemaining(field -> {
                                            if (field.isObject()) {
                                                ObjectNode swappedField = objectMapper.createObjectNode();
                                                field.fields().forEachRemaining(fieldEntry -> {
                                                    swappedField.put(fieldEntry.getValue().asText(), fieldEntry.getKey());
                                                });
                                                swappedFields.add(swappedField);
                                            } else {
                                                // If the field is not an object, keep it as is
                                                swappedFields.add(field);
                                            }
                                        });

                                        swappedRecord.set(recordEntry.getKey(), swappedFields);
                                    } else {
                                        // For other fields, keep them as is
                                        swappedRecord.set(recordEntry.getKey(), recordEntry.getValue());
                                    }
                                });

                                swappedRecords.add(swappedRecord);
                            } else {
                                // If the record is not an object, keep it as is
                                swappedRecords.add(record);
                            }
                        });

                        swappedNode.set(entry.getKey(), swappedRecords);
                    } else {
                        swappedNode.set(entry.getKey(), entry.getValue());
                    }
                });

                return swappedNode.toString();
            } else {
                return jsonString;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return jsonString;
        }
    }

    public static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    public static void main(String[] args) {
        String filePath = "src/main/resources/ResponseAdmJd.json";

        try {
            String fileContent = readFileToString(filePath);
            fileContent.getBytes(StandardCharsets.UTF_8);
            String jsonCorrect = swapFields(fileContent);
            System.out.println(jsonCorrect);




        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
