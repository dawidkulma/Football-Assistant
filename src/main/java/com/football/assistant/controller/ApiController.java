package com.football.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/api_info")
public class ApiController {

    @GetMapping
    public ModelAndView getApiInfoScreen() {
        ModelAndView featuresView = new ModelAndView();
        featuresView.setViewName("api_info");
        return featuresView;
    }

}
