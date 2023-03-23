package com.group15.user.service;

import com.group15.user.model.User;
import com.group15.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    /*
    private final UserRepository userRepo;
    private final AddressRepository addressRepo;

    public UserService(UserRepository userRepo, AddressRepository addressRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User addUser(String usr_username, String usr_password,
                        String usr_first_name, String usr_last_name,
                        Address address) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(usr_password); //Todo: Was about to test creating a password hash

        User user = new User();
        user.setUsr_username(usr_username);
        user.setUsr_password(encodedPassword);
        user.setUsr_first_name(usr_first_name);
        user.setUsr_last_name(usr_last_name);
        user.setUsr_adr_id(address);
        userRepo.save(user);

        return user;
    }

    public Address addAddress(String adr_street_name, Integer adr_street_number, String adr_city, String adr_province, String adr_country, String adr_postal_code) {
        Address address = new Address();
        address.setAdr_street_name(adr_street_name);
        address.setAdr_street_number(adr_street_number);
        address.setAdr_city(adr_city);
        address.setAdr_province(adr_province);
        address.setAdr_country(adr_country);
        address.setAdr_postal_code(adr_postal_code);
        addressRepo.save(address);

        return address;
    } */


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void register(User user) {
        userRepository.save(user);
    }


    //@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsr_username(), user.getUsr_password(),
                new ArrayList<>());
    }



}
