package com.football.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/features")
public class FeaturesController {

    @GetMapping
    public ModelAndView getFeaturesScreen() {
        ModelAndView featuresView = new ModelAndView();
        featuresView.setViewName("features");
        return featuresView;
    }

}
