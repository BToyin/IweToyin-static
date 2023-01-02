package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.BlogPostService;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

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


    @GetMapping("/blog/posts/{id}")
    public String getBlogPost (@PathVariable("id") int id, ModelMap model) {
        return "blog-post";
    }


    @PostMapping(value = {"/blog/posts/{id}/subscribe"})
    public String saveSubscriber(@Valid @ModelAttribute("subscriber") Subscriber subscriber, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "blog-post";
        }
        return subscriberService.submitSubscriberForm("/blog/posts/{id}/subscribe", subscriber, redirectAttrs);
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
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5ApprovedBlogPosts());
        model.addAttribute("numberOfBlogPosts", blogPostService.getAllApprovedBlogPosts().size());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
        model.addAttribute("blogPost",blogPost);
    }
}
