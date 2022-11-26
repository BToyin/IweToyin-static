package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

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

    public boolean ExistsInDb(String email) {
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("subscriberId")
                .withIgnorePaths("lastUpdate")
                .withMatcher("email", ignoreCase());
        Subscriber probe = new Subscriber();
        probe.setEmail(email);
        Example<Subscriber> example = Example.of(probe, modelMatcher);
        return subscriberRepository.exists(example);
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
