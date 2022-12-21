package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.Contact;
import com.toyin.lerongba.entities.Subscriber;
import com.toyin.lerongba.repositories.ContactRepository;
import com.toyin.lerongba.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return (List<Contact>) contactRepository.findAll();
    }

    public Contact getContactById(int id){
        return contactRepository.findById(id).get();
    }

    public boolean existsByEmail(String email) {
        return contactRepository.existsByEmail(email);
    }

    public Contact getContactByEmail(String email){
        return contactRepository.findContactByEmail(email);
    }

    @Transactional
    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }

}
