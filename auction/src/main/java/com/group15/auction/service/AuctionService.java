package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepo;

    public AuctionService(AuctionRepository auctionRepo) {
        this.auctionRepo = auctionRepo;
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

}
