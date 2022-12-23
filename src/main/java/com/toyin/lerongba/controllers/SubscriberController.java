package com.toyin.lerongba.controllers;

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
    public String getHomePageAfterSubmission(HttpServletRequest request, ModelMap model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            if (inputFlashMap.containsKey("passed")) {
                String passed = (String) inputFlashMap.get("passed");
                model.addAttribute("passed", passed);
            } else if (inputFlashMap.containsKey("error")){
                String error = (String) inputFlashMap.get("error");
                model.addAttribute("error", error);
            }
        }
        return "home";
    }

    @PostMapping("/home/subscribe")
    public String saveSubscriber(@Valid @ModelAttribute("subscriber") Subscriber subscriber, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        return subscriberService.submitSubscriberForm("/home/subscribe", subscriber, redirectAttrs);
    }


    @ModelAttribute
    private void addHomePageModelAttributes(ModelMap model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("latest3rdAnd4thBlogPosts", blogPostService.getLatest3rdAnd4thBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        model.addAttribute("numberOfBlogPosts", blogPostService.getAllBlogPosts().size());
    }


}
