package com.group15.auction.service;

import org.springframework.stereotype.Component;

@Component
public class AuctionServiceFactory {

    private AuctionServiceFactory (AuctionService regularService, ForwardService forwardService) {
        this.regularService = regularService;
        this.forwardService = forwardService;
    }

    private final AuctionService regularService;
    private final ForwardService forwardService;

    public AbstractService getAuctionService(String type) {

        if(type == null)
            return null;

        switch (type) {
            case "forward":
                return forwardService;
            case "dutch":
                return null;
            default:
                return null;
        }
    }

    public AuctionService getAuctionService() {
        return regularService;
    }

}
