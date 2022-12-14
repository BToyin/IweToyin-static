package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping("/homepage")
    public String getHomePage(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "homepage";
    }

    @GetMapping("/homepage#about-me")
    public String getAboutMeSection(Model model) {
        return "redirect:homepage#about-me";
    }

    @GetMapping("/homepage#services")
    public String getServicesSection(Model model) {
        return "redirect:homepage#services";
    }

}
