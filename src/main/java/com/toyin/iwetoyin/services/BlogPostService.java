package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.util.AwsS3Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {
//
//    List<BlogPost> getAllBlogPosts() {
//
//    }

    public BlogPost getBlogPostByTitle(String filename) {
        BlogPost blogPost = AwsS3Util.GetBlogPostFromS3("resources/blogPages/" + filename);
        blogPost.setExcerpt(createExcerpt(blogPost.getContent()));
        return blogPost;
    }

    public List<BlogPost> getBlogPosts() {
        List<BlogPost> blogPosts = AwsS3Util.GetBlogPostsFromS3();
        blogPosts.forEach(blogPost -> {
            blogPost.setExcerpt(blogPost.getContent());
        });
        return blogPosts;
    }

    private String createExcerpt(String content) {
        return content.substring(0, 200) + "...";
    }
}
