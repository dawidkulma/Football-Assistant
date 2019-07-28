package com.football.assistant.service;

import com.football.assistant.domain.NewsPost;
import com.football.assistant.domain.PostComment;
import com.football.assistant.domain.User;
import com.football.assistant.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostCommentService {

    private PostCommentRepository postCommentRepository;

    @Autowired
    public PostCommentService(PostCommentRepository postCommentRepository) {

        this.postCommentRepository = postCommentRepository;
    }

    public void save(PostComment comment, NewsPost newsPost, User author) {

        String timestampStr = millisecondsToCustomStrDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(timestampStr);
        comment.setCreationTimestamp(timestamp);
        comment.setNewsPost(newsPost);
        comment.setAuthor(author);
        postCommentRepository.save(comment);
    }

    private String millisecondsToCustomStrDate(long timeInMilliseconds, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date resultdate = new Date(timeInMilliseconds);
        return sdf.format(resultdate);
    }

    public void deleteAllCommentsByUserId(int id) {

        List<PostComment> postComments = postCommentRepository.findAll();

        for(PostComment comment : postComments){

            if(comment.getAuthor().getId() == id) {

                postCommentRepository.delete(comment);
            }
        }
    }
}