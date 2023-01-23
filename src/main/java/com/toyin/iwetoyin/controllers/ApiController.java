package com.toyin.iwetoyin.controllers;

import com.toyin.iwetoyin.entities.BlogPost;
import com.toyin.iwetoyin.entities.Contact;
import com.toyin.iwetoyin.entities.MyUserDetails;
import com.toyin.iwetoyin.entities.Subscriber;
import com.toyin.iwetoyin.entities.User;
import com.toyin.iwetoyin.services.BlogPostService;
import com.toyin.iwetoyin.services.ContactService;
import com.toyin.iwetoyin.services.MyUserDetailsService;
import com.toyin.iwetoyin.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@RequestMapping("/api/iwetoyin")
public class ApiController {

    @Autowired
    private final SubscriberService subscriberService;
    @Autowired
    private final BlogPostService blogPostService;
    @Autowired
    private final ContactService contactService;
    @Autowired
    private final MyUserDetailsService userDetailsService;


    public ApiController(SubscriberService subscriberService, BlogPostService blogPostService, ContactService contactService, MyUserDetailsService userDetailsService) {
        this.subscriberService = subscriberService;
        this.blogPostService = blogPostService;
        this.contactService = contactService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/subscriber/all")
    public List<Subscriber> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @PostMapping("/subscriber/register")
    public ResponseEntity register(@RequestBody Subscriber subscriber) {

        if(subscriberService.existsByEmail(subscriber.getEmail())) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        subscriberService.createSubscriber(subscriber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/blog/posts/all")
    public List<BlogPost> getBlogPosts() {
        return blogPostService.getAllApprovedBlogPosts();
    }

    @PostMapping("/blog/posts/create")
    public ResponseEntity postNewBlog(@RequestBody BlogPost blogPost) {
        if(blogPostService.existsByTitle(blogPost.getTitle())) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        blogPostService.createNewBlogPost(blogPost);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/contact/all")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @PostMapping("/contact/submit")
    public ResponseEntity submitMessage(@RequestBody Contact contact) {
        contactService.createContact(contact);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/users/all")
    public List<User> getUsers() {
        return userDetailsService.getAllUsers();
    }

    @PostMapping("/users/create")
    public ResponseEntity createUser(@RequestBody User user) {

        if (userDetailsService.existsByUserName(user.getUserName())) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userDetailsService.createUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
