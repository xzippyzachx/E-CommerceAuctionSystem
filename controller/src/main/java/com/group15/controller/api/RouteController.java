package com.group15.controller.api;

import com.group15.controller.bean.User;
import com.group15.controller.service.AuthenicationService;
import com.group15.controller.service.ControllerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class RouteController {
    private final ControllerService controllerService;
    private final AuthenicationService authenicationService;

    public RouteController(ControllerService controllerService, AuthenicationService authenicationService) {
        this.controllerService = controllerService;
        this.authenicationService = authenicationService;
    }


    private static record AuctionRecord(
            Integer auc_id,
            String itm_name,
            Double auc_current_price,
            String auc_type,
            String auc_state,
            String fwd_end_time,
            String btn_name
    ) {}
    @GetMapping({"/", "auctions"})
    public String auctions(Authentication authentication, Model model) {

        JSONArray auctions = controllerService.getAllAuctions();
        List<AuctionRecord> auctionRecords = new ArrayList<>();

        for (int i = 0; i < auctions.length(); i++) {
            JSONObject auction = auctions.getJSONObject(i);

            String btn_name = "Bid";
            switch (auction.getString("auc_state")) {
                case "running":
                    btn_name = "Bid";
                    break;
                case "complete":
                    btn_name = "Pay";
                    break;
                case "expired":
                    btn_name = "View";
                    break;
                case "paid":
                    btn_name = "View";
                    break;
            }

            auctionRecords.add(new AuctionRecord(
                    auction.getInt("auc_id"),
                    auction.getJSONObject("auc_itm_id").getString("itm_name"),
                    auction.getDouble("auc_current_price"),
                    auction.getString("auc_type"),
                    auction.getString("auc_state"),
                    auction.has("fwd_end_time") ? auction.getString("fwd_end_time") : "",
                    btn_name
            ));
        }

        model.addAttribute("auctions", auctionRecords);
        model.addAttribute("user_id", authenicationService.getUserId(authentication.getName()));

        return "auctions";
    }

    @GetMapping("bidding")
    public String bidding(Authentication authentication, Model model, @RequestParam(name = "auc_id") Integer auc_id) {

        JSONObject auction = controllerService.getAuction(auc_id);

        if(auction != null) {
            model.addAttribute("auc_id", auction.getInt("auc_id"));
            model.addAttribute("auc_current_price", auction.getDouble("auc_current_price"));
            model.addAttribute("auc_state", auction.getString("auc_state"));
            model.addAttribute("auc_type", auction.getString("auc_type"));
            if(auction.getString("auc_type") == "forward")
                model.addAttribute("fwd_end_time", auction.getString("fwd_end_time"));
            if(auction.has("highest_bidder_usr_full_name"))
                model.addAttribute("highest_bidder_usr_full_name", auction.getString("highest_bidder_usr_full_name"));
            if(auction.has("highest_bidder_usr_id"))
                model.addAttribute("highest_bidder_usr_id", auction.getInt("highest_bidder_usr_id"));
            model.addAttribute("itm_name", auction.getJSONObject("auc_itm_id").getString("itm_name"));
            model.addAttribute("itm_description", auction.getJSONObject("auc_itm_id").getString("itm_description"));
        }

        model.addAttribute("usr_id", authenicationService.getUserId(authentication.getName()));

        return "bidding";
    }

    @GetMapping("payment")
    public String payment(Authentication authentication, Model model, @RequestParam(name = "auc_id") Integer auc_id) {
        JSONObject auction = controllerService.getAuction(auc_id);
        JSONObject cost = controllerService.getCost(auc_id);

        model.addAttribute("auc_id", auction.getInt("auc_id"));
        model.addAttribute("auc_state", auction.getString("auc_state"));
        model.addAttribute("auc_current_price", cost.getInt("auc_current_price"));
        model.addAttribute("itm_shipping_cost", cost.getInt("itm_shipping_cost"));
        model.addAttribute("itm_expedited_cost", cost.getInt("itm_expedited_cost"));

        model.addAttribute("owner_usr_id", cost.getInt("usr_id"));
        model.addAttribute("usr_first_name", cost.getString("usr_first_name"));
        model.addAttribute("usr_last_name", cost.getString("usr_last_name"));
        model.addAttribute("usr_street_name", cost.getString("usr_street_name"));
        model.addAttribute("usr_street_number", cost.getInt("usr_street_number"));
        model.addAttribute("usr_city", cost.getString("usr_city"));
        model.addAttribute("usr_province", cost.getString("usr_province"));
        model.addAttribute("usr_country", cost.getString("usr_country"));
        model.addAttribute("usr_postal_code", cost.getString("usr_postal_code"));

        model.addAttribute("usr_id", authenicationService.getUserId(authentication.getName()));

        return "payment";
    }

    @GetMapping("receipt")
    public String receipt(Authentication authentication, Model model, @RequestParam(name = "auc_id") Integer auc_id) {
        JSONObject receipt = controllerService.getReceipt(auc_id);

        model.addAttribute("pay_amount", receipt.getDouble("pay_amount"));

        model.addAttribute("owner_usr_id", receipt.getInt("usr_id"));
        model.addAttribute("usr_first_name", receipt.getString("usr_first_name"));
        model.addAttribute("usr_last_name", receipt.getString("usr_last_name"));
        model.addAttribute("usr_street_name", receipt.getString("usr_street_name"));
        model.addAttribute("usr_street_number", receipt.getInt("usr_street_number"));
        model.addAttribute("usr_city", receipt.getString("usr_city"));
        model.addAttribute("usr_province", receipt.getString("usr_province"));
        model.addAttribute("usr_country", receipt.getString("usr_country"));
        model.addAttribute("usr_postal_code", receipt.getString("usr_postal_code"));

        model.addAttribute("usr_id", authenicationService.getUserId(authentication.getName()));

        return "receipt";
    }

    @GetMapping({"/","/login"})
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

        String response = controllerService.signUp(user);

        if (Objects.equals(response, "Username already exists")) {
            model.addAttribute("error", response);
            return "signup";
        }

        model.addAttribute("message", response);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestBody MultiValueMap<String, String> formData, Model model) {
        String response = authenicationService.authenticate(formData.getFirst("usr_username"), formData.getFirst("usr_password"));

        System.out.println("Token: " + response);

        if(!Objects.equals(response, "Error")) {
            model.addAttribute("token", response);
            return "login";
        } else {
            model.addAttribute("token", null);
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        SecurityContextHolder.getContext().setAuthentication(null);

        return new ResponseEntity(HttpStatus.OK);
    }
}
