package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class BlogPostService {

    @Autowired
    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAllByOrderByCreatedTimeDesc();
    }

    public List<BlogPost> getLatest5BlogPosts() {
        return blogPostRepository.findTop5ByOrderByCreatedTimeDesc();
    }

    public List<BlogPost> getLatest3rdAnd4thBlogPosts() {
         List<BlogPost> latest4BlogPosts = blogPostRepository.findTop4ByOrderByCreatedTimeDesc();
         return latest4BlogPosts.subList(2,4);
    }

    public List<BlogPost> getLatest2BlogPosts() {
        return blogPostRepository.findTop2ByOrderByCreatedTimeDesc();
    }

    public BlogPost getBlogPostById(int id){
        return blogPostRepository.findById(id).get();
    }

    public BlogPost getBlogPostByTitle(String title){
        return blogPostRepository.findBlogPostByTitle(title);
    }

    public boolean existsByTitle(String title) {
        return blogPostRepository.existsByTitle(title);
    }

    public String createExcerpt(String blogContent) {
        return blogContent.substring(0,200) + "...";
    }

    @Transactional
    public void createNewBlogPost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }

}
