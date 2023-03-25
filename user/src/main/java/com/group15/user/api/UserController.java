package com.group15.user.api;

import com.group15.user.model.User;
import com.group15.user.service.UserService;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    static record NewUserRequest(
            Integer usr_id
    ) {}
    @RequestMapping(value="get-user", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public User getUserById(@RequestBody NewUserRequest request) {
        return userService.getUser(request.usr_id);
    }

    static record NewUserDetailsRequest(
            String usr_username
    ) {}
    @RequestMapping(value="get-user-details", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public UserDetails getUserById(@RequestBody NewUserDetailsRequest request) {
        return userService.loadUserByUsername(request.usr_username);
    }

    @RequestMapping(value="get-user-id", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public String getUserId(@RequestBody NewUserDetailsRequest request) {
        Integer usr_id = userService.getUser(request.usr_username).getUsr_id();
        JSONObject payload = new JSONObject();
        payload.put("usr_id", usr_id);

        return payload.toString();
    }

    @PostMapping("/sign-up")
    public String signup(@RequestBody User user) {

        if (userService.loadUserByUsername(user.getUsr_username()) != null) {
            return "Username already exists";
        }

        userService.register(user);

        return "User created successfully";
    }

    @PostMapping("reset-user-data")
    public String resetUserData() {
        userService.resetUserData();
        return "user data reset";
    }
}
