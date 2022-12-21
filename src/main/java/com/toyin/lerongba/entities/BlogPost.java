package com.toyin.lerongba.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "blog_post", schema = "lerongba")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "title")
    @NotBlank
    private String title;

    @NotBlank
    @Column(name = "author")
    private String author;

    @Column(name = "excerpt", columnDefinition = "TINYTEXT")
    private String excerpt;

    @NotEmpty
    @Column(name = "content", length = 16777215, columnDefinition = "MEDIUMTEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return getPostId().equals(blogPost.getPostId()) && getTitle().equals(blogPost.getTitle()) && getAuthor().equals(blogPost.getAuthor()) && Objects.equals(getExcerpt(), blogPost.getExcerpt()) && getContent().equals(blogPost.getContent()) && Objects.equals(getCreatedTime(), blogPost.getCreatedTime()) && Objects.equals(getLastUpdate(), blogPost.getLastUpdate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getTitle(), getAuthor(), getExcerpt(), getContent(), getCreatedTime(), getLastUpdate());
    }
}
