package com.group15.controller.api;

import com.group15.controller.bean.User;
import com.group15.controller.service.AuthenicationService;
import com.group15.controller.service.ControllerService;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
    public String auctions(Model model) {

        JSONArray auctions = controllerService.getAllAuctions();
        List<AuctionRecord> auctionRecords = new ArrayList<>();

        for (int i = 0; i < auctions.length(); i++) {
            JSONObject auction = auctions.getJSONObject(i);

            String btn_name = "bid";
            switch (auction.getString("auc_state")) {
                case "running":
                    btn_name = "Bid";
                    break;
                case "complete":
                    btn_name = "Pay";
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

        return "auctions";
    }

    @GetMapping("bidding")
    public String bidding(Model model, @RequestParam(name = "auc_id") Integer auc_id) {

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
            model.addAttribute("itm_name", auction.getJSONObject("auc_itm_id").getString("itm_name"));
            model.addAttribute("itm_description", auction.getJSONObject("auc_itm_id").getString("itm_description"));
        }

        return "bidding";
    }

    @GetMapping("payment")
    public String payment(Model model, @RequestParam(name = "auc_id") Integer auc_id) {
        JSONObject auction = controllerService.getAuction(auc_id);
        JSONObject cost = controllerService.getCost(auc_id);

        model.addAttribute("auc_id", auction.getInt("auc_id"));
        model.addAttribute("auc_state", auction.getString("auc_state"));
        model.addAttribute("auc_current_price", cost.getInt("auc_current_price"));
        model.addAttribute("itm_shipping_cost", cost.getInt("itm_shipping_cost"));
        model.addAttribute("itm_expedited_cost", cost.getInt("itm_expedited_cost"));

        return "payment";
    }

    @GetMapping("receipt")
    public String receipt(Model model, @RequestParam(name = "auc_id") Integer auc_id) {
        JSONObject receipt = controllerService.getReceipt(auc_id);

        model.addAttribute("pay_amount", receipt.getDouble("pay_amount"));

        return "receipt";
    }

    @GetMapping("/login")
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
