package com.football.assistant.controller;

import com.football.assistant.api.ApiManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam("phrase") String phrase){

        ApiManager apiManager = new ApiManager();
        String itemId;
        if((itemId = apiManager.searchLeagueByName(phrase)) != null){
            return "redirect:/league/view/" + itemId;
        } else if((itemId = apiManager.searchTeamByName(phrase)) != null){
            return "redirect:/football_club/view/" + itemId;
        } else if((itemId = apiManager.searchPlayerByName(phrase)) != null){
            return "redirect:/player/view/" + itemId;
        } else{
            return "redirect: not_found";
        }
    }
}
