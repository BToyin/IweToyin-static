//package com.toyin.iwetoyin.controllers;
//
//import com.toyin.iwetoyin.entities.Contact;
//import com.toyin.iwetoyin.services.BlogPostService;
//import com.toyin.iwetoyin.services.ContactService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.servlet.support.RequestContextUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class ContactController {
//
//    @Autowired
//    private final ContactService contactService;
//
//    @Autowired
//    private final BlogPostService blogPostService;
//
//    private final List<String> contactReasons = Arrays.asList("To provide general feedback", "To enquire about rates and services");
//
//    public ContactController(ContactService contactService, BlogPostService blogPostService) {
//        this.contactService = contactService;
//        this.blogPostService = blogPostService;
//    }
//
//    @GetMapping("/contact")
//    public String getContactPage (ModelMap model) {
//        addContactModelAttributes(model);
//        return "contact";
//    }
//
//    @PostMapping("/contact/submit")
//    public String saveContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
//        if (bindingResult.hasErrors()) {
//            return "contact";
//        }
//        contactService.createContact(contact);
//        redirectAttrs.addFlashAttribute("passed", "Thanks for your message. I will reply as soon as possible");
//        return "redirect:/contact/submit";
//    }
//
//    @GetMapping("/contact/submit")
//    public String getContactPageAfterSubmission(HttpServletRequest request, ModelMap model) {
//        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
//        if (inputFlashMap != null) {
//            String passed = (String) inputFlashMap.get("passed");
//            model.addAttribute("passed", passed);
//        }
//        addContactModelAttributes(model);
//        return "contact";
//    }
//
//    @ModelAttribute
//    private void addContactModelAttributes(ModelMap model) {
//        model.addAttribute("latest2BlogPosts", blogPostService.getLatest2ApprovedBlogPosts());
//        model.addAttribute("contactReasons", contactReasons);
//        model.addAttribute("contact", new Contact());
//    }
//
//
//}
