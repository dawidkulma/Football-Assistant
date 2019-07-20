package com.football.assistant.controller;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/features")
public class FeaturesController {

    @GetMapping
    public ModelAndView oauthAOPgetFeaturesScreen(AbstractAuthenticationToken authentication) {
        ModelAndView featuresView = new ModelAndView();
        featuresView.setViewName("features");
        return featuresView;
    }

}
