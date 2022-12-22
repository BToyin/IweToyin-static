package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
    @Autowired
    private final BlogPostService blogPostService;

    public HomepageController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPostService.getLatest3rdAnd4thBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        return "home";
    }

    @GetMapping("/")
    public String getHomePageFromIndex(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPostService.getLatest3rdAnd4thBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        return "home";
    }

    @GetMapping("/home#about-me")
    public String getAboutMeSection(Model model) {
        return "redirect:home#about-me";
    }

    @GetMapping("/home#services")
    public String getServicesSection(Model model) {
        return "redirect:home#services";
    }

}
