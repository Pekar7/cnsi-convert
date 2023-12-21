package com.example.dbspring.model;

import lombok.Data;
import java.util.List;

@Data
public class MainEntity {
    private String requestId;
    private String tableCode;
    private String tableName;
    private List<Record> records;
}
