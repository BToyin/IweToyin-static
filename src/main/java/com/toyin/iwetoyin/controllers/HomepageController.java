package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomepageController {

    @Autowired
    final private BlogPostService blogPostService;

    public HomepageController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }


    @GetMapping(value = {"/home", "/"} )
    public String getHomePage(Model model) {
        List<BlogPost> blogPost = blogPostService.getAllBlogPosts();
        model.addAttribute("latest2BlogPosts", blogPost.subList(0,2));
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPost.subList(2,4));
        return "home";
    }
}
