package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWeb {

    @Autowired
    private HelloService service;
    
    @RequestMapping("/hello")
    public String hello() {
        return service.hello("Jar");
    }
}
