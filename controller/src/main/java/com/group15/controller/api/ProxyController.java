package com.group15.controller.api;

import com.group15.controller.service.AuthenicationService;
import com.group15.controller.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("api")
@RestController
public class ProxyController {

    private final ControllerService controllerService;
    private final AuthenicationService authenicationService;

    @Autowired
    public ProxyController(ControllerService controllerService, AuthenicationService authenicationService) {
        this.controllerService = controllerService;
        this.authenicationService = authenicationService;
    }

    static record NewBidRequest(
            Integer auc_id,
            Double bid_amount
    ) {}
    @PostMapping("new-bid")
    public String newBid(Authentication authentication, @RequestBody NewBidRequest request) {
        Integer usr_id = authenicationService.getUserId(authentication.getName());

        return controllerService.newBid(request.auc_id, request.bid_amount, usr_id);
    }

    static record GetAuctionsByKeyRequest(
            String keyword
    ) {}
    @RequestMapping(value="get-auctions-by-key", produces= MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public String newBid(@RequestBody GetAuctionsByKeyRequest request) {
        return controllerService.getAuctionsByKey(request.keyword).toString();
    }

    public static record NewPaymentRequest(
             Integer auc_id,
             Double pay_amount,
             Long pay_card_number,
             String pay_person_name,
             Date pay_expiry_date,
             Integer pay_security_code,
             Boolean expedited_shipping

    ) {}
    @PostMapping("new-payment")
    public String newBid(@RequestBody NewPaymentRequest request) {
        return controllerService.newPayment(request);
    }

    @PostMapping("reset-all-data")
    public String resetData() {
        return controllerService.resetData();
    }

}
