package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogPostService {

    @Autowired
    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getBlogPosts() {
        return (List<BlogPost>) blogPostRepository.findAll();
    }

    public BlogPost getBlogPostById(int id){
        return blogPostRepository.findById(id).get();
    }

    public BlogPost getBlogPostByTitle(String title){
        return blogPostRepository.findBlogPostByTitle(title);
    }

    @Transactional
    public void createBlogPost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }

}
