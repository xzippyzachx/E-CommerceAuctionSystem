package com.group15.controller.api;

import com.group15.controller.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class ProxyController {

    private final ControllerService controllerService;

    @Autowired
    public ProxyController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    static record NewBidRequest(
            Integer auc_id,
            Double bid_amount
    ) {}
    @PostMapping("new-bid")
    public String newBid(@RequestBody NewBidRequest request) {

        return controllerService.newBid(request.auc_id, request.bid_amount);
    }

    static record GetAuctionsByKeyRequest(
            String keyword
    ) {}
    @RequestMapping(value="get-auctions-by-key", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public String newBid(@RequestBody GetAuctionsByKeyRequest request) {
        return controllerService.getAuctionsByKey(request.keyword).toString();
    }

    @PostMapping("reset-all-data")
    public String resetData() {
        return controllerService.resetData();
    }

}
