package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public class DutchService extends AbstractService {

    protected DutchService(AuctionRepository auctionRepo, BidRepository bidRepo, RestTemplateBuilder restTemplateBuilder) {
        super(auctionRepo, bidRepo, restTemplateBuilder);
    }

    @Override
    public String createNewBid(Auction auc_id, Double bid_amount) {
        //ToDO: Implement Dutch logic
        return null;
    }

    @Override
    public void broadcastCurrentAuction(Auction auction) {
        String url = "http://localhost:" + env.getProperty("controllerServer.port") + "/api/broadcast/auction-update";

        HttpEntity<Auction> request = new HttpEntity<>(auction);

        this.restTemplate.postForEntity(url, request , null);
    }
}
