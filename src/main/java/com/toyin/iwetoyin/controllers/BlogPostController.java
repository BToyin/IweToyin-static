package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BlogPostController {

    @Autowired
    final private BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/blog/posts/{fileName}")
    public String getBlogPost(@PathVariable("fileName") String fileName, ModelMap model) {
        return "blog-post";
    }

    @ModelAttribute
    private void addBlogPostPageModelAttributes(ModelMap model, @PathVariable("fileName") String fileName) {
//        BlogPost blogPost = blogPostService.getBlogPostById(id);
        BlogPost blogPost = blogPostService.getBlogPostByTitle(fileName + ".docx");
//        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5ApprovedBlogPosts());
//        model.addAttribute("numberOfBlogPosts", blogPostService.getAllApprovedBlogPosts().size());
//        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
        model.addAttribute("blogPost", blogPost);
    }
}
