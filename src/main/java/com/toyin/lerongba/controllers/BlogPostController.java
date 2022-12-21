package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class BlogPostController {


    @Autowired
    final private BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/blog")
    public String getAllBlogsPage (ModelMap model) {
        List<BlogPost> allBlogPosts = blogPostService.getAllBlogPosts();
        List<BlogPost> latest5BlogPosts = blogPostService.getLatest5BlogPosts();
        model.addAttribute("allBlogPosts", allBlogPosts);
        model.addAttribute("latest5BlogPosts", latest5BlogPosts);
        return "blog";
    }

    @GetMapping("/blog/posts/post1")
    public String getBlogPost1 (Model model) {
        return "blog-posts/post-1";
    }

    @GetMapping("/blog/posts/{id}")
    public String getBlogPost (@PathVariable("id") int id, ModelMap model) {
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        List<BlogPost> allBlogPosts = blogPostService.getAllBlogPosts();
        model.addAttribute("numberOfBlogPosts", allBlogPosts.size());
        model.addAttribute("blogPost",blogPost);
        return "blog-post";
    }

    @PostMapping("/addNewBlogPost")
    public RedirectView SaveNewBlogPost(@ModelAttribute BlogPost blogPost, RedirectAttributes redirectAttrs, HttpServletRequest request) {

        if (blogPostService.existsByTitle(blogPost.getTitle())) {
            redirectAttrs.addFlashAttribute("error", "Error! Blog post title already exists");
        } else {
            blogPostService.createNewBlogPost(blogPost);
            redirectAttrs.addFlashAttribute("passed", "Blog post has been submitted successfully!");
        }
        return new RedirectView("/addNewBlogPost",true);
    }
}
