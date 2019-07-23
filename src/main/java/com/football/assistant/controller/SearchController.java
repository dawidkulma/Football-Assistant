package com.football.assistant.controller;

import com.football.assistant.api.ApiManager;
import javafx.util.Pair;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @RequestMapping(value = "/search/results", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("phrase") String phrase, AbstractAuthenticationToken authentication){

        ApiManager apiManager = new ApiManager();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search_results");
        Map<String,String> results;

        results = apiManager.searchLeagueByName(phrase);
        if(results != null && !results.isEmpty()){
            modelAndView.addObject("type", "league");
            modelAndView.addObject("results", results);
            return modelAndView;
        }

        results = apiManager.searchTeamByName(phrase);
        if(results != null){
            modelAndView.addObject("type", "football_club");
            modelAndView.addObject("results", results);
            return modelAndView;
        }

        results = apiManager.searchPlayerByName(phrase);
        if(results != null){
            modelAndView.addObject("type", "player");
            modelAndView.addObject("results", results);
            return modelAndView;
        }

        modelAndView.setViewName("not_found");
        return modelAndView;
    }
}
