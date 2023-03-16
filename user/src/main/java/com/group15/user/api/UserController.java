package com.group15.user.api;

import com.group15.user.model.Address;
import com.group15.user.model.User;
import com.group15.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("get-all-users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    static record SignUpRequest(
            String usr_username,
            String usr_password,
            String usr_first_name,
            String usr_last_name,
            String adr_street_name,
            Integer adr_street_number,
            String adr_city,
            String adr_province,
            String adr_country,
            String adr_postal_code
    ) {}
    @PostMapping("sign-up")
    public String signUp(@RequestBody SignUpRequest request) {
        Address address = userService.addAddress(request.adr_street_name, request.adr_street_number, request.adr_city, request.adr_province, request.adr_country, request.adr_postal_code);

        userService.addUser(request.usr_username, request.usr_password, request.usr_first_name, request.usr_last_name, address);

        return "Signed up";
    }

    //Todo: Need to add other endpoints required

}
