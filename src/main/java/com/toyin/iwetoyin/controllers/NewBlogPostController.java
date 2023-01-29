package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entities.BlogPost;
import com.toyin.iwetoyin.entities.Subscriber;
import com.toyin.iwetoyin.services.BlogPostService;
import com.toyin.iwetoyin.services.SubscriberService;
import com.toyin.iwetoyin.util.AwsS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
public class NewBlogPostController {

    @Autowired
    final private BlogPostService blogPostService;

    @Autowired
    final private SubscriberService subscriberService;
    public NewBlogPostController(BlogPostService blogPostService, SubscriberService subscriberService) {
        this.blogPostService = blogPostService;
        this.subscriberService = subscriberService;
    }


    @GetMapping("/blog/new")
    public String showSubmitNewBlogPostPage(ModelMap model) {
        return "new-blog-post";
    }

    @PostMapping("/blog/new/submit")
    public String submitNewBlogPost(@Valid @ModelAttribute("blogPost") BlogPost blogPost, BindingResult bindingResult, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttrs) throws IOException {
        if (bindingResult.hasErrors()) {
            return "new-blog-post";
        }
        if (blogPostService.existsByTitle(blogPost.getTitle())) {
            redirectAttrs.addFlashAttribute("error", "Error! Blog post title already exists");
        } else {
            String fileName = Objects.requireNonNull(multipartFile.getOriginalFilename()).replaceAll("\\s", "-");
            if (!fileName.isEmpty()){
                blogPost.setPhotoFileName(fileName);
                AwsS3Util.uploadImageToS3(multipartFile);
            }
            blogPostService.createNewBlogPost(blogPost);
            redirectAttrs.addFlashAttribute("passed", "Blog post has been submitted successfully! Please wait for approval!");
        }
        return "redirect:/blog/new/submit";
    }

    @GetMapping("/blog/new/submit")
    public String submitNewBlogPost(HttpServletRequest request, ModelMap model) {
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
        return "new-blog-post";
    }

    @PostMapping("/blog/new/approve")
    public String approveBlogPost(@ModelAttribute BlogPost blogPost,  BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "new-blog-post";
        }
        BlogPost checkBlogPost = blogPostService.getBlogPostById(blogPost.getPostId());
        if (checkBlogPost != null) {
            blogPostService.approveBlogPost(checkBlogPost);
            redirectAttrs.addFlashAttribute("passed", "Blog post has been approved successfully!");
        } else {
            redirectAttrs.addAttribute("error", "error occurred");
        }
        return "redirect:/blog/new/approve";
    }

    @GetMapping("/blog/new/approve")
    public String approveBlogPost(HttpServletRequest request, ModelMap model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            if (inputFlashMap.containsKey("passed")) {
                String passed = (String) inputFlashMap.get("passed");
                model.addAttribute("passed", passed);
            } else if (inputFlashMap.containsKey("error")) {
                String error = (String) inputFlashMap.get("error");
                model.addAttribute("error", error);
            }
        }
        return "blog";
    }


    @ModelAttribute
    private void prepareBlogsPageModelAttributes(ModelMap model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("blogPost", new BlogPost());
        model.addAttribute("blogPosts", blogPostService.getAllApprovedBlogPosts());
        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5ApprovedBlogPosts());
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
        model.addAttribute("unApprovedBlogPosts", blogPostService.getAllUnApprovedBlogPosts());
    }

}
