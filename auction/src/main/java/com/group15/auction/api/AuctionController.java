package com.group15.auction.api;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.service.AuctionServiceFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/auctions")
@RestController
public class AuctionController {

    private final AuctionServiceFactory auctionServiceFactory;

    public AuctionController(AuctionServiceFactory auctionServiceFactory) {
        this.auctionServiceFactory = auctionServiceFactory;
    }

    @GetMapping(value="get-all-auctions", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getAuctions() {
        return auctionServiceFactory.getAuctionService().getAllAuctions().toString();
    }

    static record GetAuctionsByKeyRequest(
            String keyword
    ) {}
        @RequestMapping(value="get-auctions-by-key", produces=MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public String getAuctionsByKey(@RequestBody GetAuctionsByKeyRequest request) {
        return auctionServiceFactory.getAuctionService().getAuctionsByKey(request.keyword).toString();
    }

    static record GetAuctionRequest(
            Integer auc_id
    ) {}
    @RequestMapping(value="get-auction", produces=MediaType.APPLICATION_JSON_VALUE, method={RequestMethod.GET, RequestMethod.POST})
    public String getAuction(@RequestBody GetAuctionRequest request) {
        return auctionServiceFactory.getAuctionService().getAuctionJSON(request.auc_id).toString();
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

    @PostMapping("reset-auction-data")
    public String resetAuctionData() {
        auctionServiceFactory.getAuctionService().resetAuctionData();

        auctionServiceFactory.getAuctionService("forward").dataReset();
        auctionServiceFactory.getAuctionService("dutch").dataReset();

        return "Auction data reset";
    }

}
