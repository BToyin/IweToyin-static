package com.toyin.lerongba.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blog_post", schema = "lerongba")
public class BlogPost {

    private Integer postId;
    private String title;
    private String author;
    private String excerpt;
    private String content;

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

}
