//package com.toyin.iwetoyin.controllers;
//
//import com.toyin.iwetoyin.entities.BlogPost;
//import com.toyin.iwetoyin.entities.Contact;
//import com.toyin.iwetoyin.entities.MyUserDetails;
//import com.toyin.iwetoyin.entities.Subscriber;
//import com.toyin.iwetoyin.entities.User;
//import com.toyin.iwetoyin.services.BlogPostService;
//import com.toyin.iwetoyin.services.ContactService;
//import com.toyin.iwetoyin.services.MyUserDetailsService;
//import com.toyin.iwetoyin.services.SubscriberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.List;
//import java.util.Optional;
//
//@RestController()
//@RequestMapping("/api/iwetoyin")
//public class ApiController {
//
//    @Autowired
//    private final BlogPostService blogPostService;
//    @Autowired
//    private final ContactService contactService;
//    @Autowired
//    private final MyUserDetailsService userDetailsService;
//
//
//    public ApiController(BlogPostService blogPostService, ContactService contactService, MyUserDetailsService userDetailsService) {
//        this.blogPostService = blogPostService;
//        this.contactService = contactService;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @GetMapping("/blog/pages/all")
//    public List<BlogPost> getAllBlogPosts() {
//        return blogPostService.getAllApprovedBlogPosts();
//    }
//
//
//    @GetMapping("/contact/all")
//    public List<Contact> getAllContacts() {
//        return contactService.getAllContacts();
//    }
//
//    @PostMapping("/contact/submit")
//    public ResponseEntity submitMessage(@RequestBody Contact contact) {
//        contactService.createContact(contact);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//}
