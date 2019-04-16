package com.football.assistant.service;

import com.football.assistant.domain.NewsPost;
import com.football.assistant.repository.NewsPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsPostService {

    private NewsPostRepository newsPostRepository;

    @Autowired
    public NewsPostService(NewsPostRepository newsPostRepository) {
        this.newsPostRepository = newsPostRepository;
    }

    public Iterable<NewsPost> lookup() {
        return newsPostRepository.findAll();
    }

    public long total() {
        return newsPostRepository.count();
    }

}
