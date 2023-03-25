package com.group15.auction.service;

import org.springframework.stereotype.Component;

@Component
public class AuctionServiceFactory {

    private AuctionServiceFactory (AuctionService regularService, ForwardService forwardService, DutchService dutchService) {
        this.regularService = regularService;
        this.forwardService = forwardService;
        this.dutchService = dutchService;
    }

    private final AuctionService regularService;
    private final ForwardService forwardService;
    private final DutchService dutchService;

    public AbstractService getAuctionService(String type) {

        if(type == null)
            return null;

        switch (type) {
            case "forward":
                return forwardService;
            case "dutch":
                return dutchService;
            default:
                return null;
        }
    }

    public AuctionService getAuctionService() {
        return regularService;
    }

}
