package com.group15.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/broadcast")
@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    static record SendAuctionUpdateRequest(
            Integer auc_id,
            Double auc_current_price,
            String auc_state,
            String auc_type,
            String highest_bidder_usr_full_name
    ) {}
    @PostMapping("auction-update")
    public void broadcastAuctionUpdate(@RequestBody SendAuctionUpdateRequest request) {
        System.out.println("Auction ID: " + request.auc_id + " Current Price: " + request.auc_current_price + " " + request.highest_bidder_usr_full_name);
        template.convertAndSend("/broadcast/auction-update/" + request.auc_id, request);
    }
}
