package com.group15.user.service;

import com.group15.user.repository.UserRepository;
import com.group15.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void addUser(String username) {
        User user = new User();
        user.setUsr_username(username);
        userRepo.save(user);
    }

}
