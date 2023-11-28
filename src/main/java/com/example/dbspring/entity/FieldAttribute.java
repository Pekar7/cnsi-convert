package com.example.dbspring.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class FieldAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    private String attributeKey;

    private String attributeValue;

}