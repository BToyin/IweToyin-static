package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @ModelAttribute
    private void prepareHomeModel(ModelMap model) {
        List<BlogPost> blogPost = blogPostService.getBlogPosts();
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPost);
    }
    // todo: Need to get the 4 latest blog post here to display on the home page

}
