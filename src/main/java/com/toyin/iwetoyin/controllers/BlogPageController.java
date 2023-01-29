package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entities.BlogPost;
import com.toyin.iwetoyin.entities.Subscriber;
import com.toyin.iwetoyin.services.BlogPostService;
import com.toyin.iwetoyin.services.SubscriberService;
import org.apache.maven.shared.utils.io.IOUtil;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.toyin.iwetoyin.util.AwsS3Util.GetBlogPostImageFromS3;

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
    public String getBlogsPage () {
        return "blog";
    }


    @PostMapping("/blog/subscribe")
    private String saveSubscriber(@Valid @ModelAttribute("subscriber") Subscriber subscriber, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "blog";
        }
        return subscriberService.submitSubscriberForm("/blog/subscribe", subscriber, redirectAttrs);
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

    @GetMapping("/blog/image/{id}")
    public void showBlogImage(HttpServletResponse response, ModelMap model, @PathVariable("id") int id) {
        BlogPost blogPost  = blogPostService.getBlogPostById(id);
        InputStream s3Image = GetBlogPostImageFromS3(blogPost.getPhotoFileName());
        try {
            IOUtil.copy(s3Image, response.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @ModelAttribute
    private void prepareBlogsPageModelAttributes(ModelMap model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("blogPosts", blogPostService.getAllApprovedBlogPosts());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5ApprovedBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
    }

}
