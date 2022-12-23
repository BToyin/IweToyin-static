package com.toyin.lerongba.services;

import com.toyin.lerongba.entities.Contact;
import com.toyin.lerongba.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private final ContactRepository contactRepository;
    @Autowired
    private final BlogPostService blogPostService;

    public ContactService(ContactRepository contactRepository, BlogPostService blogPostService) {
        this.contactRepository = contactRepository;
        this.blogPostService = blogPostService;
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
