package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entity.BlogPost;
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
import java.util.List;

import static com.toyin.iwetoyin.util.AwsS3Util.GetBlogPostImageFromS3;

@Controller
public class BlogPageController {

    @Autowired
    BlogPostService blogPostService;

    @GetMapping("/blog")
    public String getBlogsPage () {
        return "blog";
    }

    @GetMapping("/blog/image/{fileName}")
    public void showBlogImage(HttpServletResponse response, ModelMap model, @PathVariable String fileName) {
        BlogPost blogPost  = blogPostService.getBlogPostByTitle(fileName + ".docx");
        //todo: figure out how to appropriate file extension to the file: png/jpg/jpeg etc
        InputStream s3Image = GetBlogPostImageFromS3(fileName + ".jpg");

        try {
            IOUtil.copy(s3Image, response.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @ModelAttribute
    private void prepareBlogsPageModelAttributes(ModelMap model) {
        List<BlogPost> blogPosts = blogPostService.getBlogPosts();
        model.addAttribute("blogPosts", blogPosts);
        model.addAttribute("latest2BlogPosts", blogPosts.subList(0,2));
        model.addAttribute("latest5BlogPosts", blogPosts.subList(0,5));
    }

}
