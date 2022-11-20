package com.toyin.lerongba.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BlogPostController {

    @GetMapping("/blog")
    public String getAllBlogsPage (ModelMap model) {
        return "blog";
    }

    @GetMapping("/blog/posts/post1")
    public String getBlogPost1 (Model model) {
        return "blog-posts/post-1";
    }

    @GetMapping("/blog/posts/{id}")
    public String getNextBlogPage (Model model, @PathVariable("id") int id) {
        return "blog-posts/post-" + id;
    }
}
