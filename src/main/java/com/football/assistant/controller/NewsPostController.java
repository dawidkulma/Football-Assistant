package com.football.assistant.controller;

import com.football.assistant.domain.NewsPost;
import com.football.assistant.service.NewsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping(path = "/news_post")
public class NewsPostController {

    @Autowired
    private NewsPostService newsPostService;

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        NewsPost post = newsPostService.findById(id);
        if (post == null) return "not_found";
        model.addAttribute("post", post);
        return "news_post";
    }

}
