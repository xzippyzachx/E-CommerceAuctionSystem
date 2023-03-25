package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public abstract class AbstractService {

    protected final Environment env;
    protected final AuctionRepository auctionRepo;
    protected final BidRepository bidRepo;
    protected final RestTemplate restTemplate;

    protected AbstractService(Environment env, AuctionRepository auctionRepo, BidRepository bidRepo, RestTemplateBuilder restTemplateBuilder) {
        this.env = env;
        this.auctionRepo = auctionRepo;
        this.bidRepo = bidRepo;
        this.restTemplate = restTemplateBuilder.build();
    }

    public abstract String createNewBid(Auction auc_id, Double bid_amount, Integer usr_id);

    public abstract void broadcastCurrentAuction(Auction auction);

    public abstract void dataReset();

}
