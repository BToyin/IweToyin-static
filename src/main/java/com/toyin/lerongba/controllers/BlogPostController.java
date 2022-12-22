package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.BlogPostService;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BlogPostController {

    @Autowired
    final private BlogPostService blogPostService;
    @Autowired
    final private SubscriberService subscriberService;



    public BlogPostController(BlogPostService blogPostService, SubscriberService subscriberService) {
        this.blogPostService = blogPostService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/blog")
    public String getAllBlogsPage (ModelMap model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("allBlogPosts", blogPostService.getAllBlogPosts());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5BlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        return "blog";
    }


    @GetMapping("/blog/posts/{id}")
    public String getBlogPost (@PathVariable("id") int id, ModelMap model) {
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5BlogPosts());
        model.addAttribute("numberOfBlogPosts", blogPostService.getAllBlogPosts().size());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        model.addAttribute("blogPost",blogPost);
        return "blog-post";
    }

    @PostMapping("/blog/subscribe")
    private RedirectView saveSubscriberFromBlogsArchive(@ModelAttribute Subscriber subscriber, RedirectAttributes redirectAttrs) {

        if (subscriberService.existsByEmail(subscriber.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Error! Email address already subscribed");
        } else {
            subscriberService.createSubscriber(subscriber);
            redirectAttrs.addFlashAttribute("passed", "Email address added as subscriber successfully!");
        }
        return new RedirectView("/blog/subscribe",true);
    }

    @GetMapping("/blog/subscribe")
    public String getAllBlogsPageAfterSubmission(HttpServletRequest request, ModelMap model) {
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
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("allBlogPosts", blogPostService.getAllBlogPosts());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5BlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        return "blog";
    }

    @PostMapping(value = {"/blog/posts/{id}/subscribe"})
    public RedirectView saveSubscriber(@ModelAttribute Subscriber subscriber, RedirectAttributes redirectAttrs) {

        if (subscriberService.existsByEmail(subscriber.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Error! Email address already subscribed");
        } else {
            subscriberService.createSubscriber(subscriber);
            redirectAttrs.addFlashAttribute("passed", "Email address added as subscriber successfully!");
        }
        return new RedirectView("/blog/posts/{id}/subscribe",true);
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
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5BlogPosts());
        model.addAttribute("numberOfBlogPosts", blogPostService.getAllBlogPosts().size());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
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
