package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.repositories.BlogPostRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
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
         if (latest4BlogPosts.size() < 4){
             return null;
         }
         return latest4BlogPosts.subList(2,4);
    }

    public List<BlogPost> getLatest2BlogPosts() {
        List<BlogPost> latest2BlogPosts = blogPostRepository.findTop2ByOrderByCreatedTimeDesc();
        if (latest2BlogPosts.size() < 2){
            return null;
        }
        return latest2BlogPosts;
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
        String excerpt = blogContent.substring(0,199);
        return Jsoup.parse(excerpt).text() + "...";
    }

    public String parseContent(String rawBlogContent) {
        return Jsoup.parse(rawBlogContent).text();
    }

    @Transactional
    public void createNewBlogPost(BlogPost blogPost) {
        String content = parseContent(blogPost.getRawContent());
        blogPost.setContent(content);
        blogPost.setExcerpt(createExcerpt(content));
        blogPostRepository.save(blogPost);
    }
}
