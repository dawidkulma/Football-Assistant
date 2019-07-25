package com.football.assistant.controller;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.domain.User;
import com.football.assistant.service.FootballClubService;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/football_club")
public class FootballClubController {

    @Autowired
    private FootballClubService footballClubService;

    @Autowired
    private UserService userService;

    @GetMapping("/view/{id}")
    public String oauthAOPview(@PathVariable("id") Integer id, Model model, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        FootballClub footballClub = footballClubService.findByApiId(id);
        if (footballClub == null) return "not_found";
        model.addAttribute("club", footballClub);
        if (user.getFollowedClubs().contains(footballClub)) {
            model.addAttribute("isFollowed", true);
        } else {
            model.addAttribute("isFollowed", false);
        }
        return "football_club";
    }

    @GetMapping("/follow")
    public String oauthAOPaddToFollowed(@RequestParam("clubId") int id, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        FootballClub club = footballClubService.findByApiId(id);
        club.addFollower(user);
        this.userService.getUserRepository().flush();
        return "redirect:/football_club/view/" + id + "/";
    }

    @GetMapping("/unfollow")
    public String oauthAOPunfollowAtUserPage(@RequestParam("clubId") int id, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        FootballClub club = footballClubService.findByApiId(id);
        club.removeFollower(user);
        this.userService.getUserRepository().flush();
        return "redirect:/profile/self";
    }

    @GetMapping("/unfollow/clubpage")
    public String oauthAOPunfollowAtClubPage(@RequestParam("clubId") int id, AbstractAuthenticationToken authentication) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        FootballClub club = footballClubService.findByApiId(id);
        club.removeFollower(user);
        this.userService.getUserRepository().flush();
        return "redirect:/football_club/view/" + id + "/";
    }
}
