package com.football.assistant.controller;

import com.football.assistant.domain.PostComment;
import com.football.assistant.domain.User;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/profile")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/self")
    public ModelAndView oauthAOPadmin(AbstractAuthenticationToken authentication){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);

        Set<PostComment> postComments = user.getPostsComments();
        if (postComments == null){
            modelAndView.setViewName("not_found");
            return modelAndView;
        }
        List<PostComment> comments = postComments.stream().sorted(Comparator.comparing(PostComment::getCreationTimestamp)).collect(Collectors.toList());
        modelAndView.addObject("comments", comments);

        modelAndView.setViewName("profile");
        return modelAndView;
    }

}
