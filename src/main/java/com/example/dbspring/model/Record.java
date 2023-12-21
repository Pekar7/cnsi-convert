package com.example.dbspring.model;

import lombok.Data;

@Data
public class Record {
    private String guid;
    private String beginDate;
    private String endDate;
    private String corTime;
    private Field fields;
}