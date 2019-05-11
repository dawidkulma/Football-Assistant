package com.football.assistant.controller;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.service.FootballClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/football_club")
public class FootballClubController {

    @Autowired
    private FootballClubService footballClubService;

    @GetMapping("/{id}")
    public ModelAndView getFootballClub(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        FootballClub foundClub = footballClubService.findByApiId(id);
        if(foundClub != null) {
            modelAndView.addObject("club", foundClub);
            modelAndView.setViewName("football_club");
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            modelAndView.setViewName("not_found");
        }
        return modelAndView;
    }

}
