//package com.example.dbspring.service;
//
//import com.example.dbspring.model.KeyValueModel;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.JSONArray;
//
//import java.util.Iterator;
//
//
//public class DDLGenerator {
//
//    public static void main(String[] args) {
//        String jsonArrayString = "[{\"adm_kod\":\"78\",\"type\":\"integer\"}, {\"sname_lat\":\"HZ\",\"type\":\"textField\"}, {\n" +
//                "          \"date_end\":\"2023-09-25T23:59:59.999\",\n" +
//                "          \"type\":\"dateTime\"\n" +
//                "        }]";
//
//        JSONParser parser = new JSONParser();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            JSONArray jsonArray = (JSONArray) parser.parse(jsonArrayString);
//            System.out.println(jsonArray);
//            for (int i = 0; i < jsonArray.size(); i++) {
//                dinamic(jsonArray.get(i).toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void dinamic(String toString) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            // Преобразование JSON-строки в массив JsonNode
//            JsonNode jsonArray = objectMapper.readTree(toString);
//
//            // Обработка каждого элемента массива (каждого JSON-объекта)
//            Iterator<JsonNode> iterator = jsonArray.elements();
//            while (iterator.hasNext()) {
//                JsonNode jsonNode = iterator.next();
//
//                // Извлечение значений по ключам
//                String adm_kod = jsonNode.path("adm_kod").asText();
//                String type = jsonNode.path("type").asText();
//                String sname_lat = jsonNode.path("sname_lat").asText();
//                String date_end = jsonNode.path("date_end").asText();
//
//                // Делайте что-то с извлеченными значениями
//                System.out.println("adm_kod: " + adm_kod);
//                System.out.println("type: " + type);
//                System.out.println("sname_lat: " + sname_lat);
//                System.out.println("date_end: " + date_end);
//                System.out.println(); // Добавляем пустую строку между JSON-объектами
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    private static void dinamic(String toString) {
////        ObjectMapper objectMapper = new ObjectMapper();
////        try {
////            // Преобразование JSON-строки в объект Java
////            MyDataObject myDataObject = objectMapper.readValue(jsonString, MyDataObject.class);
////
////            // Извлечение данных из объекта
////            String adm_kod = myDataObject.getAdm_kod();
////            String dataType = myDataObject.getType();
////
////            // Используйте adm_kod и dataType по своему усмотрению, например, для создания SQL-запроса
////            String sqlQuery = "CREATE TABLE example_table (" + adm_kod + " " + dataType + ");";
////            System.out.println(sqlQuery);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//
//
//    private static String generateDDL(String variableName, String dataType) {
//        // Простой пример: создаем строку с DDL
//        return "CREATE TABLE example_table (" +
//                variableName + ", " + dataType +
//                ");";
//    }
//
//    public void generateDDl() {
//    }
//}
