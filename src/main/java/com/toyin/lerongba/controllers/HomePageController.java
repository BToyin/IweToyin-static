package com.toyin.lerongba.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/contact")
    public String getContactPage (Model modelMap) {
        return "contact";
    }

    @GetMapping("/blog")
    public String getBlogPage (Model model) {
        return "blog";
    }

    @GetMapping("/blog/posts/post1")
    public String getBlogPost1 (Model model) {
        return "blog-posts/post-1";
    }

    @GetMapping("/about-me")
    public String getAboutMePage (Model model) {
        return "/index#about-me";
    }
}
