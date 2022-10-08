package com.toyin.lerongba.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/contact")
    public String getContactPage (Model modelMap) {
        return "contact";
    }

    @GetMapping("/blog")
    public String getBlogPage (Model model) {
        return "blog";
    }
}
