package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Contact;
import com.toyin.lerongba.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String getContactPage (ModelMap model) {
        List<String> contactReasons = Arrays.asList("To provide general feedback", "To enquire about rates and services");
        model.addAttribute("contactReasons", contactReasons);
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping("/contact/submit")
    public String saveContact(@ModelAttribute Contact contact, ModelMap model) {
        contactService.createContact(contact);
        model.addAttribute("passed", "Thanks for your message. I will reply as soon as possible");
        return "contact";
    }
}
