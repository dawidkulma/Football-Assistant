package com.football.assistant.service;

import com.football.assistant.domain.PostComment;
import com.football.assistant.domain.User;
import com.football.assistant.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PostCommentService {

    private PostCommentRepository postCommentRepository;

    @Autowired
    public PostCommentService(PostCommentRepository postCommentRepository) {

        this.postCommentRepository = postCommentRepository;
    }

    public void save(PostComment comment, User author) {

        String timestampStr = millisecondsToCustomStrDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(timestampStr);
        comment.setAuthor(author);
        comment.setCreationTimestamp(timestamp);
        postCommentRepository.save(comment);
    }

    private String millisecondsToCustomStrDate(long timeInMilliseconds, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date resultdate = new Date(timeInMilliseconds);
        return sdf.format(resultdate);
    }
}