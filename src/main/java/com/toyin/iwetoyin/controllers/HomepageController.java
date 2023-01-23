package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entities.Subscriber;
import com.toyin.iwetoyin.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomepageController {
    @Autowired
    private final BlogPostService blogPostService;

    public HomepageController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping(value = {"/home", "/"} )
    public String getHomePage(Model model) {
        return "home";
    }

    @GetMapping("/home#about-me")
    public String getAboutMeSection(Model model) {
        model.addAttribute("removeModalClass", true);
        return "redirect:home#about-me";
    }

    @GetMapping("/home#services")
    public String getServicesSection(Model model) {
        return "redirect:home#services";
    }

    @ModelAttribute("subscriber")
    private void prepareHomeModelAttributes(ModelMap model) {
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPostService.getLatest3rdAnd4thApprovedBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
        model.addAttribute("subscriber",new Subscriber());

    }

}
