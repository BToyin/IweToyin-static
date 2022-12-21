package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.BlogPost;
import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.BlogPostService;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@RequestMapping("/api/lerongba")
public class ApiController {

    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private BlogPostService blogPostService;
    @PostMapping("/subscriber/register")
    public ResponseEntity register(@RequestBody Subscriber subscriber) {

        if(subscriberService.existsByEmail(subscriber.getEmail())) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        subscriberService.createSubscriber(subscriber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/subscriber/all")
    public List<Subscriber> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }
    @PostMapping("/blog/posts/create")
    public ResponseEntity postNewBlog(@RequestBody BlogPost blogPost) {
        if(blogPostService.existsByTitle(blogPost.getTitle())) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String excerpt = blogPostService.createExcerpt(blogPost.getContent());
        blogPost.setExcerpt(excerpt);
        blogPostService.createNewBlogPost(blogPost);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/blog/posts/all")
    public List<BlogPost> getBlogPosts() {
        return blogPostService.getAllBlogPosts();
    }

}
