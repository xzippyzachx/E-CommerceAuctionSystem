package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.model.DutchAuction;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DutchService extends AbstractService {

    private List<Timer> timers = new ArrayList<>();

    protected DutchService(AuctionRepository auctionRepo, BidRepository bidRepo, RestTemplateBuilder restTemplateBuilder, Environment env) {
        super(env, auctionRepo, bidRepo, restTemplateBuilder);
        SetupAuctionIntervals();
    }

    @Override
    public String createNewBid(Auction auction, Double bid_amount) {
        Date now = new Date();

        if(!auction.getAuc_state().equals("running"))
            return "Auction has already been claimed";

        if(auction.getAuc_current_price().doubleValue() != bid_amount)
            return "Bid amount must be equal to " + auction.getAuc_current_price();

        Bid newBid = new Bid();

        newBid.setBid_auc_id(auction);
        newBid.setBid_amount(bid_amount);
        newBid.setBid_usr_id(1); //TODO: Pass actual usr_id
        newBid.setBid_time(now);

        bidRepo.save(newBid);

        auction.setAuc_state("complete");

        auctionRepo.save(auction);

        broadcastCurrentAuction(auction);

        return "Buy successful";
    }

    @Override
    public void broadcastCurrentAuction(Auction auction) {
        String url = "http://localhost:" + env.getProperty("controllerServer.port") + "/api/broadcast/auction-update";
        JSONObject payload = new JSONObject(auction);

        Bid bestBid = bidRepo.findBestByAuction(auction.getAuc_id());
        if(bestBid != null) {
            payload.put("highest_bidder_usr_full_name", "Zach Ross"); //ToDo: Pass actual user full name
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(payload.toString(), headers);

        this.restTemplate.postForEntity(url, request , null);
    }

    @Override
    public void dataReset() {
        SetupAuctionIntervals();
    }

    private void SetupAuctionIntervals() {
        Date now = new Date();

        for (Timer timer: timers) {
            timer.cancel();
        }
        timers.clear();

        List<Auction> auctions = auctionRepo.findByAuctionType("dutch");

        System.out.println("Now: " + now.toInstant().toString());
        for(Auction auction: auctions) {

            if(auction.getAuc_state().equals("running"))
            {
                Timer timer = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        NextAuctionInterval(auction.getAuc_id());
                    };
                };
                Date nextInterval = new Date(now.getTime() + (1000L * ((DutchAuction) auction).getDch_decrease_interval()));
                timer.schedule(tt, nextInterval);
                timers.add(timer);
                System.out.println("Scheduling AuctionId: " + auction.getAuc_id() + " for first interval: " + nextInterval.toInstant().toString());
            }
        }
    }

    private void NextAuctionInterval(Integer auc_id) {
        Date now = new Date();
        System.out.println("Interval for dutch auction AuctionId: " + auc_id + " [" + now.toInstant().toString() + "]");

        Auction auction = auctionRepo.findById(auc_id).get();

        if(!auction.getAuc_state().equals("running")) {
            return;
        }

        if(auction.getAuc_current_price() > ((DutchAuction) auction).getDch_min_price()) {
            double nextPrice = auction.getAuc_current_price() - ((DutchAuction) auction).getDch_decrease_amount();
            auction.setAuc_current_price(nextPrice);

            Timer timer = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    NextAuctionInterval(auction.getAuc_id());
                };
            };
            Date nextInterval = new Date(now.getTime() + (1000L * ((DutchAuction) auction).getDch_decrease_interval()));
            timer.schedule(tt, nextInterval);
        }

        if(auction.getAuc_current_price() <= ((DutchAuction) auction).getDch_min_price()) {
            auction.setAuc_current_price(((DutchAuction) auction).getDch_min_price());

            Timer timer = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    CompleteAuction(auction.getAuc_id());
                };
            };
            Date nextInterval = new Date(now.getTime() + (1000L * ((DutchAuction) auction).getDch_end_delay()));
            timer.schedule(tt, nextInterval);
        }

        auctionRepo.save(auction);

        broadcastCurrentAuction(auction);
    }

    private void CompleteAuction(Integer auc_id) {
        System.out.println("Ending dutch auction AuctionId: " + auc_id + " [" + new Date().toInstant().toString() + "]");

        Auction auction = auctionRepo.findById(auc_id).get();

        auction.setAuc_state("complete");

        auctionRepo.save(auction);

        broadcastCurrentAuction(auction);
    }
}
