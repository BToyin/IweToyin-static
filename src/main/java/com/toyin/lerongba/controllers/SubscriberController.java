package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class SubscriberController {

    @Autowired
    final private SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }


    @PostMapping("/add-subscriber")
    public String saveSubscriber(@ModelAttribute Subscriber subscriber, Model model) {
        if (subscriberService.existsByEmail(subscriber.getEmail())) {
            model.addAttribute("error", "Email address already subscribed.");
            return "homepage";
        }
        subscriberService.createSubscriber(subscriber);
        model.addAttribute("passed", "Email address added as subscriber successfully!");
        return "homepage";
    }

    @GetMapping("/homepage")
    public String getHomePage(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "homepage";
    }


}
