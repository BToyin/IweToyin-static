package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Contact;
import com.toyin.lerongba.services.BlogPostService;
import com.toyin.lerongba.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private final ContactService contactService;

    @Autowired
    private final BlogPostService blogPostService;

    private final List<String> contactReasons = Arrays.asList("To provide general feedback", "To enquire about rates and services");

    public ContactController(ContactService contactService, BlogPostService blogPostService) {
        this.contactService = contactService;
        this.blogPostService = blogPostService;
    }

    @GetMapping("/contact")
    public String getContactPage (ModelMap model) {
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        model.addAttribute("contactReasons", contactReasons);
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping("/contact/submit")
    public RedirectView saveContact(@ModelAttribute Contact contact, RedirectAttributes redirectAttrs) {
        contactService.createContact(contact);
        redirectAttrs.addFlashAttribute("passed", "Thanks for your message. I will reply as soon as possible");
        return new RedirectView("/contact/submit",true);
    }

    @GetMapping("/contact/submit")
    public String getContactPageAfterSubmission(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String passed = (String) inputFlashMap.get("passed");
            model.addAttribute("passed", passed);
        }
        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2BlogPosts());
        model.addAttribute("contactReasons", contactReasons);
        model.addAttribute("contact", new Contact());
        return "contact";
    }


}
