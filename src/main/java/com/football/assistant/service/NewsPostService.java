package com.football.assistant.service;

import com.football.assistant.domain.NewsPost;
import com.football.assistant.domain.User;
import com.football.assistant.repository.NewsPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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

    public void save(NewsPost post, User author) {
        String timestampStr = millisecondsToCustomStrDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(timestampStr);
        post.setAuthor(author);
        post.setCreationTimestamp(timestamp);
        newsPostRepository.save(post);
    }

    public NewsPost findById(int id) {
        Optional<NewsPost> post = newsPostRepository.findById(id);
        return post.isPresent() ? post.get() : null;
    }

    public void deleteById(int id) {
        newsPostRepository.deleteById(id);
    }

    private String millisecondsToCustomStrDate(long timeInMilliseconds, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date resultdate = new Date(timeInMilliseconds);
        return sdf.format(resultdate);
    }

}
