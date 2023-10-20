package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.BlogPost;
import com.toyin.iwetoyin.services.BlogPostService;
import org.apache.maven.shared.utils.io.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static com.toyin.iwetoyin.util.AwsS3Util.GetBlogPostImageFromS3;

@Controller
public class BlogPageController {

    @Autowired
    BlogPostService blogPostService;

    @GetMapping("/blog")
    public String getBlogsPage () {
        return "blog";
    }

    @GetMapping("/blog/image/{id}")
    public void showBlogImage(HttpServletResponse response, ModelMap model) {
        BlogPost blogPost  = blogPostService.getBlogPostByTitle("123.docx");
        InputStream s3Image = GetBlogPostImageFromS3(blogPost.getPhotoFileName());
        try {
            IOUtil.copy(s3Image, response.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    @ModelAttribute
//    private void prepareBlogsPageModelAttributes(ModelMap model) {
//        model.addAttribute("blogPosts", blogPostService.getAllApprovedBlogPosts());
//        model.addAttribute("latest5BlogPosts", blogPostService.getLatest5ApprovedBlogPosts());
//        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
//    }

}
