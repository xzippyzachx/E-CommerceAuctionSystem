package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForwardService extends AbstractService {

    public ForwardService(AuctionRepository auctionRepo, BidRepository bidRepo, RestTemplateBuilder restTemplateBuilder) {
        super(auctionRepo, bidRepo, restTemplateBuilder);
    }

    @Override
    public String createNewBid(Auction auction, Double bid_amount) {

        Date now = new Date();

        if(auction.getAuc_end_time().compareTo(now) < 0)
            return "Auction has already ended at " + auction.getAuc_end_time();

        if(auction.getAuc_current_price() >= bid_amount)
            return "Bid amount must be larger than " + auction.getAuc_current_price();

        Bid newBid = new Bid();

        newBid.setBid_auc_id(auction);
        newBid.setBid_amount(bid_amount);
        newBid.setBid_usr_id(1); //TODO: Pass actual usr_id
        newBid.setBid_time(now);

        bidRepo.save(newBid);

        auction.setAuc_current_price(bid_amount);

        auctionRepo.save(auction);

        broadcastCurrentAuction(auction);

        return "Bid successful";
    }

    @Override
    public void broadcastCurrentAuction(Auction auction) {
        String url = "http://localhost:" + env.getProperty("controllerServer.port") + "/api/broadcast/auction-update";

        HttpEntity<Auction> request = new HttpEntity<>(auction);

        this.restTemplate.postForEntity(url, request , null);
    }
}
