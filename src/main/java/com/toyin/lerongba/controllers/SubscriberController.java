package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.BlogPostService;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class SubscriberController {

    @Autowired
    final private SubscriberService subscriberService;

    @Autowired
    final private BlogPostService blogPostService;

    public SubscriberController(SubscriberService subscriberService, BlogPostService blogPostService) {
        this.subscriberService = subscriberService;
        this.blogPostService = blogPostService;
    }


    @GetMapping("/home/subscribe")
    public String getHomePageAfterSubmission(HttpServletRequest request, Model model) {
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
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPostService.getLatest3rdAnd4thBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        return "home";
    }

    @PostMapping("/home/subscribe")
    public RedirectView saveSubscriber(@ModelAttribute Subscriber subscriber, RedirectAttributes redirectAttrs) {

        if (subscriberService.existsByEmail(subscriber.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Error! Email address already subscribed");
        } else {
            subscriberService.createSubscriber(subscriber);
            redirectAttrs.addFlashAttribute("passed", "Email address added as subscriber successfully!");
        }
        return new RedirectView("/home/subscribe",true);
    }
}
