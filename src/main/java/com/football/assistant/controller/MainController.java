package com.football.assistant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage() {
        return "Welcome to the Football Assistant App!";
    }

}
