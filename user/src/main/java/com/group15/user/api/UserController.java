package com.group15.user.api;

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

    static record NewUserRequest(
            String usr_username
    ) {}
    @PostMapping("add-user")
    public void addUser(@RequestBody NewUserRequest request) {
        userService.addUser(request.usr_username());
    }

}
