package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.util.AwsS3Util;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BlogPostService {
    private static final int EXCERPT_LENGTH = 200;

    public BlogPost getBlogPostByTitle(String title) {
        List<BlogPost> blogPosts  = getAllBlogPosts();
        return blogPosts.stream().filter(bg -> bg.getFileName().equals(AwsS3Util.transformToLowerCaseWithDash(title))).findFirst().get();
    }

    public List<BlogPost> getAllBlogPosts() {
        List<BlogPost> blogPosts = AwsS3Util.getAllBlogPostsFromS3();
        blogPosts.forEach(blogPost -> {
            blogPost.setExcerpt(createExcerpt(blogPost.getContent().toString()));
        });
        return blogPosts;
    }

    // Changed this to 1 to account for the '[' from the array
    private String createExcerpt(String content) {
        return content.substring(1, EXCERPT_LENGTH) + "...";
    }

    public BlogPost getPreviousBlogPost(String nextPostTitle) {
        BlogPost currentPost = getBlogPostByTitle(AwsS3Util.transformToLowerCaseWithDash(nextPostTitle));
        List<BlogPost> allBlogPosts = getAllBlogPosts();
        Collections.reverse(allBlogPosts);
        return allBlogPosts.get(currentPost.getPostId() - 2);
    }

    public BlogPost getNextBlogPost(String previousPostTitle) {
        BlogPost currentPost = getBlogPostByTitle(AwsS3Util.transformToLowerCaseWithDash(previousPostTitle));
        List<BlogPost> allBlogPosts = getAllBlogPosts();
        Collections.reverse(allBlogPosts);
        return allBlogPosts.get(currentPost.getPostId());
    }
}
