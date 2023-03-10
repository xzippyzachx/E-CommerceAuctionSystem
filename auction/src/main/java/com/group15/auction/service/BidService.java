package com.group15.auction.service;

import com.group15.auction.model.Bid;
import com.group15.auction.repository.BidRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepo;

    public BidService(BidRepository bidRepo) {
        this.bidRepo = bidRepo;
    }

    public List<Bid> getAllBids() {
        return bidRepo.findAll();
    }

    public List<Bid> getAuctionBids(Integer auc_id) {
        return bidRepo.findByAuction(auc_id);
    }

}
