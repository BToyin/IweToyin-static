package com.toyin.iwetoyin.repositories;

import com.toyin.iwetoyin.entities.Contact;
import com.toyin.iwetoyin.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Override
    Optional<Contact> findById(Integer integer);

    Contact findContactByEmail(String email);

    boolean existsByEmail(String email);
}
