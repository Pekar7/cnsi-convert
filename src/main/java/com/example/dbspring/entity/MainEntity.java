package com.example.dbspring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "MainEntity")
public class MainEntity {

    @Id
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("tableCode")
    private String tableCode;

    @JsonProperty("tableName")
    private String tableName;

    @JsonProperty("records")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "main_entity_id")
    private List<Record> records;

}
