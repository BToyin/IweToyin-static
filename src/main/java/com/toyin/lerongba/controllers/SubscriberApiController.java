package com.toyin.lerongba.controllers;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class SubscriberApiController {

    @Autowired
    private SubscriberService subscriberService;
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody Subscriber subscriber) {
        subscriberService.createSubscriber(subscriber);
        return new ResponseEntity(HttpStatus.OK);
    }

}
