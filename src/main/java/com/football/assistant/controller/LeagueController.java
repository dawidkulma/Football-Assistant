package com.football.assistant.controller;

import com.football.assistant.domain.League;
import com.football.assistant.domain.User;
import com.football.assistant.service.LeagueService;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/league")
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private UserService userService;

    @GetMapping("/view/{id}")
    public String oauthAOPview(@PathVariable("id") Integer id, Model model, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        League league = leagueService.findByApiId(id);
        if (league == null)
            return "not_found";
        model.addAttribute("league", league);
        if (user.getFollowedClubs().contains(league)) {
            model.addAttribute("isFollowed", true);
        } else {
            model.addAttribute("isFollowed", false);
        }
        return "league";
    }

    @GetMapping("/follow")
    public String oauthAOPaddToFollowed(@RequestParam("leagueId") int id, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        League league = leagueService.findByApiId(id);
        league.addFollower(user);
        this.userService.getUserRepository().flush();
        return "redirect:/league/view/" + id + "/";
    }

    @GetMapping("/unfollow")
    public String oauthAOPunfollowAtUserPage(@RequestParam("leagueId") int id, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        League league = leagueService.findByApiId(id);
        league.removeFollower(user);
        this.userService.getUserRepository().flush();
        return "redirect:/profile/self";
    }

    @GetMapping("/unfollow/leaguepage")
    public String oauthAOPunfollowAtLeaguePage(@RequestParam("leagueId") int id, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        League league = leagueService.findByApiId(id);
        league.removeFollower(user);
        this.userService.getUserRepository().flush();
        return "redirect:/league/view/" + id + "/";
    }
}
