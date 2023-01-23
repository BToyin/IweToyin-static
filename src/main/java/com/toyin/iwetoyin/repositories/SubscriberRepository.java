package com.toyin.iwetoyin.repositories;

import com.toyin.iwetoyin.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    @Override
    Optional<Subscriber> findById(Integer integer);

    Subscriber findSubscriberByEmail(String email);

    boolean existsByEmail(String email);
}
