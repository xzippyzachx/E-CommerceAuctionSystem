package com.group15.controller.api;

import com.group15.controller.bean.Auction;
import com.group15.controller.service.ControllerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {
    String title = "EECS4413 Auction House";

    private final ControllerService controllerService;

    public RouteController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }

    @GetMapping("bidding")
    public String bidding(Model model, @RequestParam(name = "auc_id") Integer auc_id) {

        Auction auction = controllerService.getAuction(auc_id);

        if(auction != null) {
            model.addAttribute("auc_id", auction.getAuc_id());
            model.addAttribute("auc_current_price", auction.getAuc_current_price());
            model.addAttribute("auc_state", auction.getAuc_state());
        }

        return "bidding";
    }
}
