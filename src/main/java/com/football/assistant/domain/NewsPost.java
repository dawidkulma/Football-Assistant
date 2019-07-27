package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "news_post")
public class NewsPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer id;

    @Column(length = 512)
    private String title;

    @Column(length = 4096)
    private String content;

    @Column
    @Basic
    private java.sql.Timestamp creationTimestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "newsPost", cascade = CascadeType.ALL)
    private Set<PostComment> postsComments;

    @Column
    private String fotoUrl;

    @Transient
    private String digest;

    public NewsPost(String title, String content, Timestamp creationTimestamp, String fotoUrl) {
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
        this.fotoUrl = fotoUrl;
        this.postsComments = new HashSet<>();
    }

    public NewsPost() {

        this.postsComments = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Set<PostComment> getPostsComments() {

        return postsComments;
    }

    public void setPostsComments(Set<PostComment> postsComments) {

        this.postsComments = postsComments;
    }

    public void addPostComment(PostComment postComment){
        if(!this.postsComments.contains(postComment)) {
            this.postsComments.add(postComment);
            postComment.setNewsPost(this);
        }
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        author.addNewsPost(this);
    }
}
