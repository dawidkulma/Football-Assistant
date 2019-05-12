package com.football.assistant.controller;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.service.FootballClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/football_club")
public class FootballClubController {

    @Autowired
    private FootballClubService footballClubService;

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        FootballClub footballClub = footballClubService.findByApiId(id);
        if (footballClub == null) return "not_found";
        model.addAttribute("club", footballClub);
        return "football_club";
    }

}
