package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractService {

    protected final AuctionRepository auctionRepo;
    protected final BidRepository bidRepo;

    protected AbstractService(AuctionRepository auctionRepo, BidRepository bidRepo) {
        this.auctionRepo = auctionRepo;
        this.bidRepo = bidRepo;
    }

    public abstract String createNewBid(Auction auc_id, Double bid_amount);

}
