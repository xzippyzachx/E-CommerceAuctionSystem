package com.group15.user.api;

import com.group15.user.model.User;
import com.group15.user.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/users")
@RestController
public class UserController {


//    private final UserService userService;
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
//
//    //Todo: Need to add other endpoints required

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/login") // "/signup"
        public String showLoginPage() {
            return "login";
        }

        @GetMapping("/signup")
        public String showSignupPage(Model model) {
            model.addAttribute("user", new User());
            return "signup";
        }

        @PostMapping("/signup") //take care of the @valid
        public String signup(@ModelAttribute("user") /*@Valid */ User user, BindingResult result, Model model) {
            if (result.hasErrors()) {
                return "signup";
            }
            if (userService.loadUserByUsername(user.getUsr_username()) != null) {
                model.addAttribute("error", "Username already exists");
                return "signup";
            }
            userService.register(user);
            model.addAttribute("message", "User created successfully");
            return "redirect:/";
        }

        @PostMapping("/login")
        public String login(@RequestParam String username, @RequestParam String password, Model model) {
            try {
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/home";
            } catch (AuthenticationException e) {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        }

        @GetMapping("/home")
        public String showHomePage() {
            return "home";
        }

        @GetMapping("/logout")
        public String logout() {
            SecurityContextHolder.getContext().setAuthentication(null);
            return "redirect:/";
        }

}
