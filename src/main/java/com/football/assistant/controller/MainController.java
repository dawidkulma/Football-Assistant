package com.football.assistant.controller;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.domain.User;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        StringBuilder followedClubsJson = new StringBuilder("{ \"clubs\": [ ");
        for (FootballClub club : user.getFollowedClubs()) {
            followedClubsJson.append(club.getApiId() + ",");
        }
        followedClubsJson.deleteCharAt(followedClubsJson.length()-1);
        followedClubsJson.append("] }");

        modelAndView.addObject("followedTeamsIds", followedClubsJson.toString());
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

}
