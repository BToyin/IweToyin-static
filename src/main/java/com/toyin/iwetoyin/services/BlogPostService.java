package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.util.AwsS3Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {
    //todo: change back to 200
    private static final int EXCERPT_LENGTH = 50;

//
//    List<BlogPost> getAllBlogPosts() {
//
//    }

    public BlogPost getBlogPostByTitle(String filename) {
        BlogPost blogPost = AwsS3Util.GetBlogPostFromS3("resources/blogPages/" + filename);
        blogPost.setExcerpt(createExcerpt(blogPost.getContent().toString()));
        return blogPost;
    }

    public List<BlogPost> getBlogPosts() {
        List<BlogPost> blogPosts = AwsS3Util.GetBlogPostsFromS3();
        blogPosts.forEach(blogPost -> {
            blogPost.setExcerpt(createExcerpt(blogPost.getContent().toString()));
        });
        return blogPosts;
    }

    //todo: changed this to 1 to account for the '[' from the array
    private String createExcerpt(String content) {
        return content.substring(1, EXCERPT_LENGTH) + "...";
    }
}
