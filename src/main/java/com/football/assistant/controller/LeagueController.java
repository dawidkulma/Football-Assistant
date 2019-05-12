package com.football.assistant.controller;

import com.football.assistant.domain.League;
import com.football.assistant.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/league")
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        League league = leagueService.findByApiId(id);
        if (league == null) return "not_found";
        model.addAttribute("league", league);
        return "league";
    }

}
