package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForwardService extends AbstractService {

    public ForwardService(AuctionRepository auctionRepo, BidRepository bidRepo) {
        super(auctionRepo, bidRepo);
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

        return "Bid successful";
    }
}
