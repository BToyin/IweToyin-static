package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriberService {

    @Autowired
    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public List<Subscriber> getAllSubscribers() {
        return (List<Subscriber>) subscriberRepository.findAll();
    }

    public Subscriber getSubscriberById(int id){
        return subscriberRepository.findById(id).get();
    }

    public boolean existsByEmail(String email) {
        return subscriberRepository.existsByEmail(email);
    }

    public Subscriber getSubscriberByEmail(String email){
        return subscriberRepository.findSubscriberByEmail(email);
    }

    @Transactional
    public void createSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }

}
