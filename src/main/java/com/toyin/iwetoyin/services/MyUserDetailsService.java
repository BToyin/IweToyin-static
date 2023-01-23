package com.toyin.iwetoyin.services;

import com.toyin.iwetoyin.entities.MyUserDetails;
import com.toyin.iwetoyin.entities.Subscriber;
import com.toyin.iwetoyin.entities.User;
import com.toyin.iwetoyin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found:" + userName));
        return user.map(MyUserDetails::new).get();
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    };

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }
}
