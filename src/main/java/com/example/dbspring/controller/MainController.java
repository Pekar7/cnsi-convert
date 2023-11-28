package com.example.dbspring.controller;
import com.example.dbspring.entity.MainEntity;
import com.example.dbspring.repo.MainRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class MainController {

    @Autowired
    MainRepo mainRepo;

    @GetMapping("/hello")
    public MainEntity main() {
        String filePath = "src/main/resources/response.json";

        try {

            String fileContent = readFileToString(filePath);
            System.out.println("Было " + fileContent);

            String correct = transformJson(fileContent); //подмена
            ObjectMapper mapper= new ObjectMapper();
            System.out.println("Стало " + correct);

            MainEntity main = jsonToJava(correct);
            assert main != null;
            mainRepo.save(main);

            return jsonToJava(correct);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    public static String transformJson(String inputJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputJson);

            if (jsonNode.has("records")) { // узел рекорда
                jsonNode.get("records").forEach(record -> {
                    if (record.has("fields")) { //если есть field
                        record.get("fields").forEach(field -> {
                            if (field.has("name") && field.has("value") && field.has("type")) {
                                String name = field.get("name").asText();
                                String value = field.get("value").asText();
                                String type = field.get("type").asText();

                                ((ObjectNode) field).remove("name");
                                ((ObjectNode) field).remove("value");
                                ((ObjectNode) field).remove("type");

                                ((ObjectNode) field).put(name, value);
                                ((ObjectNode) field).put("type", type);
                            }
                        });
                    }
                });
            }

            return objectMapper.writeValueAsString(jsonNode);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public static MainEntity jsonToJava(String json) {
        String prettyJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, MainEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}