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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class BlogPageController {

    @Autowired
    final private BlogPostService blogPostService;

    @Autowired
    final private SubscriberService subscriberService;
    public BlogPageController(BlogPostService blogPostService, SubscriberService subscriberService) {
        this.blogPostService = blogPostService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/blog")
    public String getAllBlogsPage (ModelMap model) {
        return "blog";
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
        return "blog";
    }


//    @PostMapping("/addNewBlogPost")
//    public RedirectView SaveNewBlogPost(@ModelAttribute BlogPost blogPost, RedirectAttributes redirectAttrs, HttpServletRequest request) {
//
//        if (blogPostService.existsByTitle(blogPost.getTitle())) {
//            redirectAttrs.addFlashAttribute("error", "Error! Blog post title already exists");
//        } else {
//            blogPostService.createNewBlogPost(blogPost);
//            redirectAttrs.addFlashAttribute("passed", "Blog post has been submitted successfully!");
//        }
//        return new RedirectView("/addNewBlogPost",true);
//    }


    @PostMapping("/blog/subscribe")
    private String saveSubscriber(@Valid @ModelAttribute("subscriber") Subscriber subscriber, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "blog";
        }
        return subscriberService.submitSubscriberForm("/blog/subscribe", subscriber, redirectAttrs);
    }

    @ModelAttribute
    private void prepareBlogsPageModelAttributes(ModelMap model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("allBlogPosts", blogPostService.getAllBlogPosts());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5BlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
    }

}
