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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
        model.addAttribute("post", post);

        Set<PostComment> postComments = post.getPostsComments();
        if (postComments == null){
            return "not_found";
        }
        List<PostComment> comments = postComments.stream().sorted(Comparator.comparing(PostComment::getCreationTimestamp)).collect(Collectors.toList());
        if(comments.isEmpty()){
            model.addAttribute("anyComments", "No comments yet :(");
        }
        else{
            model.addAttribute("anyComments", "");
        }
        model.addAttribute("comments", comments);

        PostComment newComment = new PostComment();
        model.addAttribute("newComment", newComment);

        return "news_post";
    }

    @PostMapping("/view/{id}/comment/save")
    public String savePostComment(@PathVariable("id") Integer id, @ModelAttribute("post") NewsPost post, @ModelAttribute("comment") PostComment comment) {

        User commentAuthor = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        postCommentService.save(comment, post, commentAuthor);

        return "redirect:/news_post/view/" + id.toString() + "/" + "#last_comments";
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
