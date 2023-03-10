package com.group15.auction.api;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.service.AuctionService;
import com.group15.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/auctions")
@RestController
public class AuctionController {

    private final AuctionService auctionService;
    private final BidService bidService;

    @Autowired
    public AuctionController(AuctionService auctionService, BidService bidService) {
        this.auctionService = auctionService;
        this.bidService = bidService;
    }

    @GetMapping("get-all-auctions")
    public List<Auction> getAuctions() {
        return auctionService.getAllAuctions();
    }

    static record NewGetAuctionRequest(
            Integer auc_id
    ) {}
    @GetMapping("get-auction")
    public Auction getAuction(@RequestBody NewGetAuctionRequest request) {
        return auctionService.getAuction(request.auc_id);
    }

    @GetMapping("get-all-bids")
    public List<Bid> getBids() {
        return bidService.getAllBids();
    }

    static record NewGetBidsRequest(
            Integer auc_id
    ) {}
    @GetMapping("get-auction-bids")
    public List<Bid> getAuctionBids(@RequestBody NewGetAuctionRequest request) {
        return bidService.getAuctionBids(request.auc_id);
    }

}
