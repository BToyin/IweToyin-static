package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.util.AwsS3Util;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BlogPostService {
    //todo: change back to 200
    private static final int EXCERPT_LENGTH = 50;

    public BlogPost getBlogPostByTitle(String filename) {
        BlogPost blogPost = AwsS3Util.GetBlogPostFromS3("resources/blogPages/" + filename);
        blogPost.setExcerpt(createExcerpt(blogPost.getContent().toString()));
        return blogPost;
    }

    public List<BlogPost> getAllBlogPosts() {
        List<BlogPost> blogPosts = AwsS3Util.GetAllBlogPostsFromS3();
        blogPosts.forEach(blogPost -> {
            blogPost.setExcerpt(createExcerpt(blogPost.getContent().toString()));
        });
        return blogPosts;
    }

    //todo: changed this to 1 to account for the '[' from the array
    private String createExcerpt(String content) {
        return content.substring(1, EXCERPT_LENGTH) + "...";
    }

    public BlogPost getPreviousBlogPost(String currentPostTitle) {
        BlogPost currentPost = getBlogPostByTitle(AwsS3Util.transformToLowerCaseWithDash(currentPostTitle) + ".docx");
        List<BlogPost> allBlogPosts = getAllBlogPosts();
        Collections.reverse(allBlogPosts);
        return allBlogPosts.get(currentPost.getPostId() - 2);
    }

    public BlogPost getNextBlogPost(String currentPostTitle) {
        BlogPost currentPost = getBlogPostByTitle(AwsS3Util.transformToLowerCaseWithDash(currentPostTitle) + ".docx");
        List<BlogPost> allBlogPosts = getAllBlogPosts();
        Collections.reverse(allBlogPosts);
        return allBlogPosts.get(currentPost.getPostId());
    }
}
