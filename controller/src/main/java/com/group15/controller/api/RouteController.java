package com.group15.controller.api;

import com.group15.controller.bean.Auction;
import com.group15.controller.service.ControllerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteController {
    private final ControllerService controllerService;

    public RouteController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }


    private static record AuctionRecord(
            Integer auc_id,
            String itm_name,
            Double auc_current_price,
            String auc_type,
            String auc_state,
            String fwd_end_time
    ) {}
    @GetMapping({"/", "auctions"})
    public String auctions(Model model) {

        JSONArray auctions = controllerService.getAllAuctions();
        List<AuctionRecord> auctionRecords = new ArrayList<>();

        for (int i = 0; i < auctions.length(); i++) {
            JSONObject auction = auctions.getJSONObject(i);
            auctionRecords.add(new AuctionRecord(
                    auction.getInt("auc_id"),
                    auction.getJSONObject("auc_itm_id").getString("itm_name"),
                    auction.getDouble("auc_current_price"),
                    auction.getString("auc_type"),
                    auction.getString("auc_state"),
                    auction.has("fwd_end_time") ? auction.getString("fwd_end_time") : ""
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
            model.addAttribute("itm_name", auction.getJSONObject("auc_itm_id").getString("itm_name"));
            model.addAttribute("itm_description", auction.getJSONObject("auc_itm_id").getString("itm_description"));
        }

        return "bidding";
    }
}
