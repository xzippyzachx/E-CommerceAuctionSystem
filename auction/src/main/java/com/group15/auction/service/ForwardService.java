package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.model.ForwardAuction;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class ForwardService extends AbstractService {

    public ForwardService(AuctionRepository auctionRepo, BidRepository bidRepo, RestTemplateBuilder restTemplateBuilder) {
        super(auctionRepo, bidRepo, restTemplateBuilder);

        SetupAuctionEndings();
    }

    @Override
    public String createNewBid(Auction auction, Double bid_amount) {

        Date now = new Date();

        if(!((ForwardAuction) auction).getAuc_state().equals("running"))
            return "Auction has already ended at " + ((ForwardAuction) auction).getFwd_end_time();

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

    private void SetupAuctionEndings() {
        Date now = new Date();

        List<Auction> auctions = auctionRepo.findByAuctionType("forward");

        System.out.println("Now: " + now.toInstant().toString());
        for (Auction auction: auctions) {

            if(((ForwardAuction) auction).getFwd_end_time().compareTo(now) > 0)
            {
                Timer timer = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        CompleteAuction(auction.getAuc_id());
                    };
                };
                timer.schedule(tt,((ForwardAuction)auction).getFwd_end_time());
                System.out.println("Scheduling AuctionId: " + auction.getAuc_id() + " for Date: " + ((ForwardAuction)auction).getFwd_end_time().toInstant().toString());

            } else if (((ForwardAuction) auction).getAuc_state().equals("running")) {
                CompleteAuction(auction.getAuc_id());
            }

        }
    }

    private void CompleteAuction(Integer auc_id) {
        System.out.println("Ending auction AuctionId: " + auc_id + " [" + new Date().toInstant().toString() + "]");

        Auction auction = auctionRepo.findById(auc_id).get();

        auction.setAuc_state("complete");

        auctionRepo.save(auction);

        broadcastCurrentAuction(auction);
    }

}
