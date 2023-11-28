package com.example.dbspring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static com.example.dbspring.Main2.readFileToString;

public class JsonConverter {

    public static void main(String[] args) throws Exception {
        String inputJson = "{ \"requestId\": \"571c9d75-6681-4efa-82e8-1286a18a9591\", \"tableCode\": \"ic00_admjd\", \"tableName\": \"ic00_admjd. Администрации железных дорог\", \"records\": [ { \"guid\": \"030ebed0-b7c8-4508-8d0c-a13cf449b244\", \"beginDate\": \"2005-12-01T00:00:00\", \"endDate\": \"2015-07-09T00:00:00\", \"corTime\": \"2015-07-29T11:36:10.355\", \"fields\": [ { \"name\": \"version_no\", \"type\": \"integer\", \"value\": 2 }, { \"name\": \"operation_type\", \"type\": \"textField\", \"value\": \"U\" }, { \"name\": \"adm_kod\", \"type\": \"integer\", \"value\": 33 }, { \"name\": \"sname_lat\", \"type\": \"textField\", \"value\": \"KZD\" }, { \"name\": \"external_id\", \"type\": \"textField\", \"value\": \"100215\" }, { \"name\": \"date_end\", \"type\": \"dateTime\", \"value\": \"2015-07-09T00:00:00\" }, { \"name\": \"active_flag\", \"type\": \"flag\", \"value\": false }, { \"name\": \"system_create_stamp\", \"type\": \"dateTime\", \"value\": \"2015-07-29T11:36:10.355\" }, { \"name\": \"stran_kod\", \"type\": \"ic00_stran\", \"value\": 156 }, { \"name\": \"date_begin\", \"type\": \"dateTime\", \"value\": \"2005-12-01T00:00:00\" }, { \"name\": \"sname_rus\", \"type\": \"textField\", \"value\": \"КЗД\" }, { \"name\": \"record_union_no\", \"type\": \"textField\", \"value\": \"IC00.H_ADMJD-33\" }, { \"name\": \"name\", \"type\": \"textField\", \"value\": \"Китайские железные дороги\" }, { \"name\": \"system_update_stamp\", \"type\": \"dateTime\", \"value\": \"2015-07-29T11:36:10.355\" }, { \"name\": \"status\", \"type\": \"record_statuses\", \"value\": \"ORIGINAL\" } ] }, { \"guid\": \"0333f5ed-ec84-4d59-b2dd-cc0552f05b7d\", \"beginDate\": \"2000-01-01T00:00:00\", \"endDate\": \"2023-09-25T23:59:59.999\", \"corTime\": \"2011-09-07T16:21:02.649\", \"fields\": [ { \"name\": \"version_no\", \"type\": \"integer\", \"value\": 1 }, { \"name\": \"operation_type\", \"type\": \"textField\", \"value\": \"I\" }, { \"name\": \"adm_kod\", \"type\": \"integer\", \"value\": 78 }, { \"name\": \"sname_lat\", \"type\": \"textField\", \"value\": \"HZ\" }, { \"name\": \"system_user_stamp\", \"type\": \"access_users\", \"value\": \"admin\" }, { \"name\": \"external_id\", \"type\": \"textField\", \"value\": \"100040\" }, { \"name\": \"date_end\", \"type\": \"dateTime\", \"value\": \"2023-09-25T23:59:59.999\" }, { \"name\": \"active_flag\", \"type\": \"flag\", \"value\": false }, { \"name\": \"system_create_stamp\", \"type\": \"dateTime\", \"value\": \"2011-09-07T16:21:02.649\" }, { \"name\": \"date_begin\", \"type\": \"dateTime\", \"value\": \"2000-01-01T00:00:00\" }, { \"name\": \"sname_rus\", \"type\": \"textField\", \"value\": \"ХЗ\" }, { \"name\": \"record_union_no\", \"type\": \"textField\", \"value\": \"IC00.H_ADMJD-78\" }, { \"name\": \"name\", \"type\": \"textField\", \"value\": \"Железные дороги Хорватии\" }, { \"name\": \"system_update_stamp\", \"type\": \"dateTime\", \"value\": \"2011-09-07T16:21:02.649\" }, { \"name\": \"status\", \"type\": \"record_statuses\", \"value\": \"ORIGINAL\" } ] } ] }";

        String fileContent = readFileToString("src/main/resources/response2.json");
        String outputJson = transformJson(inputJson);

    }



    public static String transformJson(String inputJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputJson);

            if (jsonNode.has("records")) {
                jsonNode.get("records").forEach(record -> {
                    if (record.has("fields")) {
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
}