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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.toyin.iwetoyin.util.AwsS3Util.GetBlogPostImageFromS3;

@Controller
public class BlogController {

    final int PAGE_SIZE = 3;

    @Autowired
    BlogPostService blogPostService;

    @GetMapping("/blog")
    public ModelAndView getBlogsPage(@RequestParam(name = "page", defaultValue = "1") int page) {
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();

        int totalPosts = blogPosts.size();
        int totalPages = (int) Math.ceil((double) totalPosts / PAGE_SIZE);
        int endIndex = Math.min(page * PAGE_SIZE, totalPosts);

        List<BlogPost> currentPagePosts = blogPosts.subList(0, endIndex);

        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("blogPosts", currentPagePosts);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", totalPages);
        modelAndView.addObject("latest2BlogPosts", blogPosts.subList(0,2));
        modelAndView.addObject("latest5BlogPosts", blogPosts.subList(0,5));

        return modelAndView;
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
}
