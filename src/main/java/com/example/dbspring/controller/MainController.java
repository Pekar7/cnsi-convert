package com.example.dbspring.controller;

import com.example.dbspring.service.AdmJd2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

    private final AdmJd2Service admJd2Service;

    @Autowired
    public MainController(AdmJd2Service admJd2Service) {
        this.admJd2Service = admJd2Service;
    }

    @GetMapping("/hello")
    public String main() {
        String filePath = "src/main/resources/jsonExampleCnsi/response.json";
        admJd2Service.refactorAndSaveRequest(filePath);
        return "Successfully add cnsi information in database";
    }

    @GetMapping("/ddl")
    public String createDDL() throws IOException {
        return "DDL";
    }

}