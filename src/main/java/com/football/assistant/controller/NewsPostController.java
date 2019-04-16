//package com.football.assistant.controller;
//
//import com.football.assistant.domain.NewsPost;
//import com.football.assistant.service.NewsPostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.validation.Valid;
//import java.util.LinkedList;
//import java.util.List;
//
//
//@RestController
//@RequestMapping(path = "/news_posts")
//public class NewsPostController {
//
//    @Autowired
//    private NewsPostService newsPostService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    public List<NewsPost> getNewsPosts() {
//        List<NewsPost> result = new LinkedList<>();
//        newsPostService.lookup().forEach(post -> result.add(post));
//        return result;
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ModelAndView createNewsPost(@Valid NewsPost post, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("news_post");
//        } else {
//            newsPostRepository.save(post);
//            modelAndView.addObject("successMessage", "News post added successfully");
//            modelAndView.addObject("newsPost", new NewsPost());
//            modelAndView.setViewName("news_post");
//        }
//        return modelAndView;
//    }
//
//}
