package com.example.dbspring.service;

import com.example.dbspring.model.Field;
import com.example.dbspring.model.MainEntity;
import com.example.dbspring.model.Record;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class AdmJd2Service {

    private final DataSource dataSource;

    @Autowired
    public AdmJd2Service(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void refactorAndSaveRequest(String filePath) {
        try {
            String fileContent = readFileToString(filePath); //берем json cnsi
            String correct = transformJson(fileContent); //применяем key:value
            log.info("Key:Value JSON " + correct);
            String origin = refactor(correct); // записываем json в нужный формат
            log.info("Finally JSON " + origin);

            ObjectMapper objectMapper = new ObjectMapper();
            MainEntity main = objectMapper.readValue(origin, MainEntity.class);
            log.info("Deserialization completed successfully " + main.getRequestId());
            List<Record> records = main.getRecords();

            for (Record record : records) {
                insertData(record.getFields(), main.getTableCode().substring(5, 10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void insertData(Field field, String tableName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();

            String sqlQuery = "INSERT INTO " + tableName +
                    "(activeFlag, admKod, blocked_by_user, dateBegin, dateEnd, externalId, name, operationType, " +
                    "recordUnionNo, record_uuid, snameLat, snameRus, status, stran_kod, systemCreateStamp, " +
                    "systemUpdateStamp, systemUserStamp, system_author_stamp, type, versionNo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, field.getActiveFlag());
            preparedStatement.setString(2, field.getAdmKod());
            preparedStatement.setString(3, field.getBlockedByUser());
            preparedStatement.setString(4, field.getDateBegin());
            preparedStatement.setString(5, field.getDateEnd());
            preparedStatement.setString(6, field.getExternalId());
            preparedStatement.setString(7, field.getName());
            preparedStatement.setString(8, field.getOperationType());
            preparedStatement.setString(9, field.getSnameRus());
            preparedStatement.setString(10, field.getRecord_uuid());
            preparedStatement.setString(11, field.getSnameLat());
            preparedStatement.setString(12, field.getSnameRus());
            preparedStatement.setString(13, field.getStatus());
            preparedStatement.setString(14, field.getStran_kod());
            preparedStatement.setString(15, field.getSystemCreateStamp());
            preparedStatement.setString(16, field.getSystemUpdateStamp());
            preparedStatement.setString(17, field.getSystemUserStamp());
            preparedStatement.setString(18, field.getSystem_author_stamp());
            preparedStatement.setString(19, field.getType());
            preparedStatement.setString(20, field.getVersionNo());

            preparedStatement.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String refactor(String correct) {
        JsonObject inputJsonObject = JsonParser.parseString(correct).getAsJsonObject();

        JsonArray recordsArray = inputJsonObject.getAsJsonArray("records");
        if (recordsArray != null && recordsArray.size() > 0) {
            for (JsonElement recordElement : recordsArray) {
                if (recordElement.isJsonObject()) {
                    JsonObject recordObject = recordElement.getAsJsonObject();
                    JsonElement fieldsElement = recordObject.get("fields");
                    if (fieldsElement != null) {
                        if (fieldsElement.isJsonArray()) {
                            JsonArray fieldsArray = fieldsElement.getAsJsonArray();
                            JsonObject fieldsObject = new JsonObject();
                            for (JsonElement fieldElement : fieldsArray) {
                                if (fieldElement.isJsonObject()) {
                                    JsonObject fieldObject = fieldElement.getAsJsonObject();
                                    for (String key : fieldObject.keySet()) {
                                        fieldsObject.add(key, fieldObject.get(key));
                                    }
                                }
                            }
                            recordObject.remove("fields");
                            recordObject.add("fields", fieldsObject);
                        } else if (fieldsElement.isJsonObject()) {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        }
        return inputJsonObject.toString();
    }

    public static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    public static String transformJson(String inputJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputJson);

            String tableCode = jsonNode.path("tableCode").asText();
            String table = tableCode.substring(5, 10);
            String script = "";

            if (jsonNode.has("records")) {
                JsonNode records = jsonNode.get("records");
                int recordsSize = records.size();

                for (int i = 0; i < recordsSize; i++) {
                    JsonNode record = records.get(i);

                    if (record.has("fields")) {
                        JsonNode fields = record.get("fields");
                        int fieldsSize = fields.size();

                        for (int j = 0; j < fieldsSize; j++) {
                            JsonNode field = fields.get(j);

                            if (field.has("name") && field.has("value") && field.has("type")) {
                                String name = field.get("name").asText();
                                String value = field.get("value").asText();
                                String type = field.get("type").asText();

                                ((ObjectNode) field).remove("name");
                                ((ObjectNode) field).remove("value");
                                ((ObjectNode) field).remove("type");

                                ((ObjectNode) field).put(name, value);
                                ((ObjectNode) field).put("type", type);

                                if (type.equals("textField")) {
                                    String typePostgres = type.replace("textField", "VARCHAR");
                                    if (j == fieldsSize - 1) {
                                        script += name + " " + typePostgres + " ";
                                    } else {
                                        script += name + " " + typePostgres + ", ";
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("T" + table);
            createDDL(table, script);
            return objectMapper.writeValueAsString(jsonNode);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void createDDL(String tableCode, String script) {
        String ddlScript = "CREATE TABLE " + tableCode + " ("
                + script + ");";
        System.out.println();
        System.out.println(ddlScript);
        System.out.println();
    }
}
