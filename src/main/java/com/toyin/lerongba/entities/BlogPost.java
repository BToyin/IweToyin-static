package com.toyin.lerongba.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "blog_post", schema = "lerongba")
public class BlogPost {

    private Integer postId;
    private String title;
    private String author;
    private String excerpt;
    private String content;
    private Timestamp lastUpdate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    public Integer getPostId() { return postId; }

    public void setPostId(Integer postId) { this.postId = postId; }

    @Basic
    @Column(name = "title")
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    @Basic
    @Column(name = "author")
    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    @Basic
    @Column(name = "excerpt", columnDefinition = "TINYTEXT")
    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Basic
    @Column(name = "content", length = 16777215, columnDefinition = "MEDIUMTEXT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "last_update")
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return getPostId().equals(blogPost.getPostId()) && Objects.equals(getTitle(), blogPost.getTitle()) && Objects.equals(getAuthor(), blogPost.getAuthor()) && Objects.equals(getExcerpt(), blogPost.getExcerpt()) && Objects.equals(getContent(), blogPost.getContent()) && Objects.equals(getLastUpdate(), blogPost.getLastUpdate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getTitle(), getAuthor(), getExcerpt(), getContent(), getLastUpdate());
    }
}
