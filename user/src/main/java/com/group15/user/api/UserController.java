package com.group15.user.api;

import com.group15.user.model.User;
import com.group15.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("api/users")
@RestController
public class UserController {


 //   private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("get-all-users")
//    public List<User> getUsers() {
//        return userService.getAllUsers();
//    }
//
//    static record SignUpRequest(
//            String usr_username,
//            String usr_password,
//            String usr_first_name,
//            String usr_last_name,
//            String adr_street_name,
//            Integer adr_street_number,
//            String adr_city,
//            String adr_province,
//            String adr_country,
//            String adr_postal_code
//    ) {}
//    @PostMapping("sign-up")
//    public String signUp(@RequestBody SignUpRequest request) {
//        Address address = userService.addAddress(request.adr_street_name, request.adr_street_number, request.adr_city, request.adr_province, request.adr_country, request.adr_postal_code);
//
//        userService.addUser(request.usr_username, request.usr_password, request.usr_first_name, request.usr_last_name, address);
//
//        return "Signed up";
//    }

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }


//    public ArrayList<> get-user(Long id){
//        User user = userService.getUser(id);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.UserDetails(user.getUsr_username(), user.getUsr_password(),
//                user.getUsr_first_name(),user.getUsr_last_name(),user.getUsr_street_name(),user.getUsr_street_number(),
//                user.getUsr_city(),user.getUsr_province(),user.getUsr_country(),user.getUsr_postal_code(),
//                new ArrayList<>());
//    }

    static record NewUserRequest(
            Integer user_id
    ) {}
    @RequestMapping(value="get-user", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public User getUserById(@RequestBody NewUserRequest request) {
        return userService.getUser(request.user_id);
    }

    static record NewUserDetailsRequest(
            String usr_username
    ) {}
    @RequestMapping(value="get-user-details", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public UserDetails getUserById(@RequestBody NewUserDetailsRequest request) {
        return userService.loadUserByUsername(request.usr_username);
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
