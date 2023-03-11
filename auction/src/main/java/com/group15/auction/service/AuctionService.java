package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    protected final AuctionRepository auctionRepo;
    protected final BidRepository bidRepo;

    public AuctionService(AuctionRepository auctionRepo, BidRepository bidRepo) {
        this.auctionRepo = auctionRepo;
        this.bidRepo = bidRepo;
    }

    public List<Auction> getAllAuctions() {
        return auctionRepo.findAll();
    }

    public Auction getAuction(Integer auc_id) {
        try {
            return auctionRepo.findById(auc_id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Bid> getAllBids() {
        return bidRepo.findAll();
    }

    public List<Bid> getAuctionBids(Integer auc_id) {
        return bidRepo.findByAuction(auc_id);
    }

    public Bid getAuctionBestBid(Integer auc_id) {
        return bidRepo.findBestByAuction(auc_id);
    }

}
