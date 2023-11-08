package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entity.BlogPost;
import com.toyin.iwetoyin.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BlogPageController {

    @Autowired
    final private BlogPostService blogPostService;

    public BlogPageController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/blog/pages/{fileName}")
    public ModelAndView getBlogPage(@PathVariable("fileName") String fileName) {
        BlogPost blogPost = blogPostService.getBlogPostByTitle(fileName);
        ModelAndView modelAndView = new ModelAndView("blog-page");
        modelAndView.addObject("blogPost", blogPost);
        modelAndView.addObject("paragraphs", blogPost.getContent());
        return modelAndView;
    }

    @GetMapping("/blog/pages/next")
    public ModelAndView getNextBlogPage(@RequestParam("previousPostTitle") String previousPostTitle) {
        BlogPost nextPost = blogPostService.getNextBlogPost(previousPostTitle);
        ModelAndView modelAndView = new ModelAndView("blog-page");
        modelAndView.addObject("blogPost", nextPost);
        modelAndView.addObject("paragraphs", nextPost.getContent());
        return modelAndView;
    }

    @GetMapping("/blog/pages/previous")
    public ModelAndView getPreviousBlogPage(@RequestParam("nextPostTitle") String nextPostTitle) {
        BlogPost previousPost = blogPostService.getPreviousBlogPost(nextPostTitle);
        ModelAndView modelAndView = new ModelAndView("blog-page");
        modelAndView.addObject("blogPost", previousPost);
        modelAndView.addObject("paragraphs", previousPost.getContent());
        return modelAndView;
    }


    @ModelAttribute
    private void addBlogPostPageModelAttributes(ModelMap model) {
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        model.addAttribute("latest2BlogPosts", blogPosts.subList(0,2));
        model.addAttribute("latest5BlogPosts", blogPosts.subList(0,5));
        model.addAttribute("numberOfBlogPosts", blogPosts.size());
    }
}
