package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "post_comment")
public class PostComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 2048)
    private String content;

    @Column
    @Basic
    private java.sql.Timestamp creationTimestamp;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private NewsPost newsPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public PostComment(String content, Timestamp creationTimestamp) {

        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    public PostComment() {}

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public Timestamp getCreationTimestamp() {

        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {

        this.creationTimestamp = creationTimestamp;
    }

    public NewsPost getNewsPost() {

        return newsPost;
    }

    public void setNewsPost(NewsPost newsPost) {

        this.newsPost = newsPost;
        newsPost.addPostComment(this);
    }

    public User getAuthor() {

        return author;
    }

    public void setAuthor(User author) {

        this.author = author;
        author.addPostComment(this);
    }
}