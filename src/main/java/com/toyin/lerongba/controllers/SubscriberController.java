package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class SubscriberController {

    @Autowired
    final private SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/homepage/subscribe")
    public RedirectView saveSubscriber(@ModelAttribute Subscriber subscriber, RedirectAttributes redirectAttrs, HttpServletRequest request) {

        if (subscriberService.existsByEmail(subscriber.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Error! Email address already subscribed");
        } else {
            subscriberService.createSubscriber(subscriber);
            redirectAttrs.addFlashAttribute("passed", "Email address added as subscriber successfully!");
        }
        return new RedirectView("/homepage/subscribe",true);
    }

    @GetMapping("/homepage/subscribe")
    public String getHomePageAfterSubmission(HttpServletRequest request, Model model) {
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
        model.addAttribute("subscriber", new Subscriber());
        return "/homepage";
    }
}
