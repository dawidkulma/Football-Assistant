package com.football.assistant.controller;

import com.football.assistant.api.ApiManager;
import com.football.assistant.utils.SearchObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @RequestMapping(value = "/search/results", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("phrase") String phrase, AbstractAuthenticationToken authentication){

        ApiManager apiManager = new ApiManager();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search_results");

        List<SearchObject> results_leagues;
        List<SearchObject> results_teams;
        List<SearchObject> results_players;
        List<SearchObject> results = new ArrayList<>();

        results_leagues = apiManager.searchLeagueByName(phrase);

        phrase = phrase.trim();
        phrase = phrase.replaceAll("\\s", "%20");

        results_teams = apiManager.searchTeamByName(phrase);
        results_players = apiManager.searchPlayerByName(phrase);

        if(results_leagues != null && !results_leagues.isEmpty())
            results.addAll(results_leagues);

        if(results_teams != null && !results_teams.isEmpty())
            results.addAll(results_teams);

        if(results_players != null && !results_players.isEmpty())
            results.addAll(results_players);

        if(results.isEmpty()) {
            modelAndView.setViewName("not_searched");
            return modelAndView;
        }

        modelAndView.addObject("results", results);
        return modelAndView;
    }
}
