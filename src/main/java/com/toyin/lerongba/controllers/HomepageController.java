package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "home";
    }

    @GetMapping("/")
    public String getHomePage1(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "home";
    }

    @GetMapping("/home#about-me")
    public String getAboutMeSection(Model model) {
        return "redirect:home#about-me";
    }

    @GetMapping("/home#services")
    public String getServicesSection(Model model) {
        return "redirect:home#services";
    }

}
