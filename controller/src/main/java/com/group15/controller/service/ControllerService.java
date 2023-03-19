package com.group15.controller.service;

import com.group15.controller.bean.Auction;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ControllerService {

    protected final Environment env;
    protected final RestTemplate restTemplate;

    public ControllerService(Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.env = env;
        this.restTemplate = restTemplateBuilder.build();
    }

    public static record GetAuction(
            Integer auc_id
    ) {}
    public Auction getAuction(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-auction";

        GetAuction auctionPayload = new GetAuction(auc_id);
        HttpEntity<GetAuction> request = new HttpEntity<>(auctionPayload);

        ResponseEntity<Auction> response = this.restTemplate.postForEntity(url, request, Auction.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record PostBid(
            Integer auc_id,
            Double bid_amount
    ) {}
    public String newBid(Integer auc_id, Double bid_amount) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/new-bid";

        PostBid bidPayload = new PostBid(auc_id, bid_amount);
        HttpEntity<PostBid> request = new HttpEntity<>(bidPayload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return response.getBody();
    }

}
