package com.group15.user.service;

import com.group15.user.model.User;
import com.group15.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void register(User user) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getUsr_password());

        user.setUsr_password(encodedPassword);

        userRepository.save(user);
    }

    public User getUser(Integer user_id){
        User user = userRepository.findByUserId(user_id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getUser(String user_username){
        User user = userRepository.findByUserName(user_username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    //@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            return null;
        }

        return new org.springframework.security.core.userdetails.User(user.getUsr_username(), user.getUsr_password(), new ArrayList<>());
    }

    public void resetUserData() {
        userRepository.resetUserData();
    }

}
