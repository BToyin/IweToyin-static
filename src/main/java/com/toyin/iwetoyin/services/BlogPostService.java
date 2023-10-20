package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.BlogPost;
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
        return AwsS3Util.GetBlogPostFromS3(filename);
    }
}
