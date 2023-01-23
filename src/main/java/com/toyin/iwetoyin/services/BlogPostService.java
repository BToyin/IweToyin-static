package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.entities.BlogPost;
import com.toyin.iwetoyin.repositories.BlogPostRepository;
import org.jsoup.Jsoup;
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

    public List<BlogPost> getAllApprovedBlogPosts() {
        return blogPostRepository.findAllByApprovedTrueOrderByCreatedTimeDesc();
    }

    public List<BlogPost> getAllUnApprovedBlogPosts() {
        return blogPostRepository.findAllByApprovedFalseOrderByCreatedTimeDesc();
    }

    public List<BlogPost> getLatest5ApprovedBlogPosts() {
        return blogPostRepository.findTop5ByApprovedTrueOrderByCreatedTimeDesc();
    }

    public List<BlogPost> getLatest3rdAnd4thApprovedBlogPosts() {
         List<BlogPost> latest4BlogPosts = blogPostRepository.findTop4ByApprovedTrueOrderByCreatedTimeDesc();
         if (latest4BlogPosts.size() < 4){
             return null;
         }
         return latest4BlogPosts.subList(2,4);
    }

    public List<BlogPost> getLatest2ApprovedBlogPosts() {
        List<BlogPost> latest2BlogPosts = blogPostRepository.findTop2ByApprovedTrueOrderByCreatedTimeDesc();
        if (latest2BlogPosts.size() < 2){
            return null;
        }
        return latest2BlogPosts;
    }

    public BlogPost getBlogPostById(int id){
        return blogPostRepository.findById(id).orElse(null);
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
        blogPost.setApproved(false);
        blogPostRepository.save(blogPost);
    }
    @Transactional
    public void approveBlogPost(BlogPost blogPost) {
        blogPost.setApproved(true);
        blogPostRepository.save(blogPost);
    }

    public BlogPost getBlogPostByTitle(String title) {
        return blogPostRepository.findByTitle(title).orElse(null);
    }
}
