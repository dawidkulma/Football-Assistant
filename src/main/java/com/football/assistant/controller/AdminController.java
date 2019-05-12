package com.football.assistant.controller;

import com.football.assistant.domain.NewsPost;
import com.football.assistant.domain.User;
import com.football.assistant.service.NewsPostService;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsPostService newsPostService;

    @GetMapping("/summary")
    public ModelAndView admin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView users() {
        List<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }


    @GetMapping("/posts")
    public ModelAndView posts() {
        Iterable<NewsPost> posts = newsPostService.lookup();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_posts");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/posts/add")
    public ModelAndView addPost() {
        NewsPost post = new NewsPost();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_post_form");
        modelAndView.addObject("post", post);
        return modelAndView;
    }


    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute("post") NewsPost post) {
        User author = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        newsPostService.save(post, author);
        return "redirect:/admin/posts";
    }

    @GetMapping("/posts/delete")
    public String deletePost(@RequestParam("postId") int id) {
        newsPostService.deleteById(id);
        return "redirect:/admin/posts";
    }

}
