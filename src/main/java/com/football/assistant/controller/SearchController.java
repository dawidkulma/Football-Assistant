package com.football.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam("phrase") String phrase){

        return "redirect: ...";
    }
}
