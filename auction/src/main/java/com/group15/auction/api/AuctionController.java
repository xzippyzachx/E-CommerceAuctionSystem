package com.group15.auction.api;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.service.AuctionServiceFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/auctions")
@RestController
public class AuctionController {

    private final AuctionServiceFactory auctionServiceFactory;

    public AuctionController(AuctionServiceFactory auctionServiceFactory) {
        this.auctionServiceFactory = auctionServiceFactory;
    }

    @GetMapping("get-all-auctions")
    public List<Auction> getAuctions() {
        return auctionServiceFactory.getAuctionService().getAllAuctions();
    }

    static record GetAuctionRequest(
            Integer auc_id
    ) {}
    @RequestMapping(value = "get-auction", method = { RequestMethod.GET, RequestMethod.POST })
    public Auction getAuction(@RequestBody GetAuctionRequest request) {
        return auctionServiceFactory.getAuctionService().getAuction(request.auc_id);
    }

    @GetMapping("get-all-bids")
    public List<Bid> getBids() {
        return auctionServiceFactory.getAuctionService().getAllBids();
    }

    static record GetBidsRequest(
            Integer auc_id
    ) {}
    @GetMapping("get-auction-bids")
    public List<Bid> getAuctionBids(@RequestBody GetBidsRequest request) {
        return auctionServiceFactory.getAuctionService().getAuctionBids(request.auc_id);
    }

    static record GetBestBidRequest(
            Integer auc_id
    ) {}
    @GetMapping("get-best-bid")
    public Bid getAuctionBestBid(@RequestBody GetBestBidRequest request) {
        return auctionServiceFactory.getAuctionService().getAuctionBestBid(request.auc_id);
    }

    static record NewBidRequest(
            Integer auc_id,
            Double bid_amount
    ) {}
    @PostMapping("new-bid")
    public String newBid(@RequestBody NewBidRequest request) {

        Auction auction = auctionServiceFactory.getAuctionService().getAuction(request.auc_id);

        return auctionServiceFactory.getAuctionService(auction.getAuc_type()).createNewBid(auction, request.bid_amount);
    }

    @PutMapping("reset-auction-data")
    public String resetAuctionData() {
        auctionServiceFactory.getAuctionService().resetAuctionData();

        auctionServiceFactory.getAuctionService("forward").dataReset();
        auctionServiceFactory.getAuctionService("dutch").dataReset();

        return "Data reset";
    }

}
