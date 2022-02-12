package com.example.sara.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/healthcheck")
public class HealtCheckController {

    @GetMapping
    public String getHealtCheck(){
        return "OK";
    }
}
