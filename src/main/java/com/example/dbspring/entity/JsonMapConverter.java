//package com.example.dbspring.entity;
//
//import aj.org.objectweb.asm.TypeReference;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.Converter;
//import org.hibernate.mapping.Map;
//
//import java.io.IOException;
//
//@Converter
//public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {
//
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(Map<String, Object> attribute) {
//        try {
//            return objectMapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            // Обработка ошибки
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Map<String, Object> convertToEntityAttribute(String dbData) {
//        if (dbData == null) {
//            return null;
//        }
//
//        try {
//            return objectMapper.readValue(dbData, new TypeReference<>() {});
//        } catch (IOException e) {
//            // Обработка ошибки
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
