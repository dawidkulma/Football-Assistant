package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "news_post")
public class NewsPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public NewsPost(String title, String content, Timestamp creationTimestamp) {
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    public NewsPost() {}

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        author.addNewsPost(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsPost newsPost = (NewsPost) o;
        return id.equals(newsPost.id) &&
                Objects.equals(title, newsPost.title) &&
                Objects.equals(content, newsPost.content) &&
                Objects.equals(creationTimestamp, newsPost.creationTimestamp) &&
                Objects.equals(author, newsPost.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, creationTimestamp, author);
    }

    @Override
    public String toString() {
        return "NewsPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationTimestamp=" + creationTimestamp +
                ", author=" + author +
                '}';
    }
}
