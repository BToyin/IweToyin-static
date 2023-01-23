package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entities.BlogPost;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelMap model = new ModelMap();
        model.addAttribute("blogPost", new BlogPost());
        model.addAttribute("error", "Error! File size should be less than 3MB");
        return "new-blog-post";
    }
}
