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

    @GetMapping("/blog/posts/{id}")
    public String getBlogPost(@PathVariable("id") int id, ModelMap model) {
        return "blog-post";
    }

    @GetMapping(value = {"/blog/posts/{id}/subscribe"})
    public String getBlogPageAfterSubmission(HttpServletRequest request, ModelMap model, @PathVariable int id) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            if (inputFlashMap.containsKey("passed")) {
                String passed = (String) inputFlashMap.get("passed");
                model.addAttribute("passed", passed);
            } else {
                String error = (String) inputFlashMap.get("error");
                model.addAttribute("error", error);
            }
        }
        return "blog-post";
    }

    @ModelAttribute
    private void addBlogPostPageModelAttributes(ModelMap model, @PathVariable int id) {
//        BlogPost blogPost = blogPostService.getBlogPostById(id);
        BlogPost blogPost = blogPostService.getBlogPostByTitle("stress-exhibit.docx");
//        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5ApprovedBlogPosts());
//        model.addAttribute("numberOfBlogPosts", blogPostService.getAllApprovedBlogPosts().size());
//        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
        model.addAttribute("blogPost", blogPost);
    }
}
