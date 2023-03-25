package com.group15.auction.service;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.model.ForwardAuction;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForwardService extends AbstractService {

    private List<Timer> timers = new ArrayList<>();

    public ForwardService(AuctionRepository auctionRepo, BidRepository bidRepo, RestTemplateBuilder restTemplateBuilder, Environment env) {
        super(env, auctionRepo, bidRepo, restTemplateBuilder);

        SetupAuctionEndings();
    }

    @Override
    public String createNewBid(Auction auction, Double bid_amount, Integer usr_id) {
        Date now = new Date();

        if(!auction.getAuc_state().equals("running"))
            return "Auction has already ended at " + ((ForwardAuction) auction).getFwd_end_time();

        if(auction.getAuc_current_price() >= bid_amount)
            return "Bid amount must be larger than " + auction.getAuc_current_price();

        Bid newBid = new Bid();

        newBid.setBid_auc_id(auction);
        newBid.setBid_amount(bid_amount);
        newBid.setBid_usr_id(usr_id);
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
        JSONObject payload = new JSONObject(auction);

        Bid bestBid = bidRepo.findBestByAuction(auction.getAuc_id());
        if(bestBid != null) {
            JSONObject userJSON = getUser(bestBid.getBid_usr_id());
            String fullName = userJSON.getString("usr_first_name") + " " + userJSON.getString("usr_last_name");
            payload.put("highest_bidder_usr_full_name", fullName);
            payload.put("highest_bidder_usr_id", bestBid.getBid_usr_id());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(payload.toString(), headers);

        this.restTemplate.postForEntity(url, request, null); //ToDO: Try catch
    }

    @Override
    public void dataReset() {
        SetupAuctionEndings();
    }

    private void SetupAuctionEndings() {
        Date now = new Date();

        for (Timer timer: timers) {
            timer.cancel();
        }
        timers.clear();

        List<Auction> auctions = auctionRepo.findByAuctionType("forward");

        System.out.println("Now: " + now.toInstant().toString());
        for(Auction auction: auctions) {

            if(((ForwardAuction) auction).getFwd_end_time().compareTo(now) > 0)
            {
                Timer timer = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        ExpireAuction(auction.getAuc_id());
                    };
                };
                timer.schedule(tt,((ForwardAuction)auction).getFwd_end_time());
                timers.add(timer);
                System.out.println("Scheduling AuctionId: " + auction.getAuc_id() + " for Date: " + ((ForwardAuction)auction).getFwd_end_time().toInstant().toString());

            } else if (((ForwardAuction) auction).getAuc_state().equals("running")) {
                System.out.println("AuctionId: " + auction.getAuc_id() + " End Time: " + ((ForwardAuction) auction).getFwd_end_time().toInstant().toString());
                ExpireAuction(auction.getAuc_id());
            }
        }
    }

    private void ExpireAuction(Integer auc_id) {
        System.out.println("Ending forward auction AuctionId: " + auc_id + " [" + new Date().toInstant().toString() + "]");

        Auction auction = auctionRepo.findById(auc_id).get();

        auction.setAuc_state("expired");

        auctionRepo.save(auction);

        broadcastCurrentAuction(auction);
    }

    public static record GetUser(
            Integer usr_id
    ) {}
    private JSONObject getUser(Integer usr_id) {
        String url = "http://localhost:" + env.getProperty("userServer.port") + "/api/users/get-user";

        AuctionService.GetUser userPayload = new AuctionService.GetUser(usr_id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("userServer.apiKey"));
        HttpEntity<AuctionService.GetUser> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONObject(response.getBody());
    }

}
