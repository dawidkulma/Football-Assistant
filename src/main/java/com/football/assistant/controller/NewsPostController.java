package com.football.assistant.controller;

import com.football.assistant.domain.NewsPost;
import com.football.assistant.domain.PostComment;
import com.football.assistant.domain.User;
import com.football.assistant.service.NewsPostService;
import com.football.assistant.service.PostCommentService;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private UserService userService;

    @GetMapping("/view/{id}")
    public String oauthAOPview(@PathVariable("id") Integer id, Model model, AbstractAuthenticationToken authentication) {

        NewsPost post = newsPostService.findById(id);
        if (post == null)
            return "not_found";
        PostComment comment = new PostComment();
        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        return "news_post";
    }

    @PostMapping("/view/{id}/comment")
    public String savePost(@PathVariable("id") Integer id, @ModelAttribute("comment") PostComment comment) {

        User author = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        postCommentService.save(comment, author);
        return "redirect:/view/{id}/";
    }

    @GetMapping("/browse")
    public ModelAndView oauthAOPgetPosts(AbstractAuthenticationToken authentication) {
        Iterable<NewsPost> posts = newsPostService.lookup();
        boolean anyPosts = false;
        for (NewsPost post : posts) {
            post.setDigest(post.getContent().substring(0, 100) + "...");
            anyPosts = true;
        }
        ModelAndView postsView = new ModelAndView();
        postsView.setViewName("posts");
        postsView.addObject("posts", posts);
        if (!anyPosts)
            postsView.addObject("anyPosts", "No posts currently to display (:");
        else
            postsView.addObject("anyPosts", "");

        return postsView;
    }

}
