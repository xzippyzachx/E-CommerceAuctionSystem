package com.group15.controller.service;

import com.group15.controller.api.ProxyController;
import com.group15.controller.bean.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    public JSONObject getAuction(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-auction";

        GetAuction auctionPayload = new GetAuction(auc_id);
        HttpEntity<GetAuction> request = new HttpEntity<>(auctionPayload);

        ResponseEntity<String> auctionResponse = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return  new JSONObject(auctionResponse.getBody());
    }

    public JSONArray getAllAuctions() {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-all-auctions";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class); //ToDO: Try catch

        return new JSONArray(response.getBody());
    }

    public static record PostAuctionByKey(
            String keyword
    ) {}
    public JSONArray getAuctionsByKey(String keyword) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-auctions-by-key";

        PostAuctionByKey auctionPayload = new PostAuctionByKey(keyword);
        HttpEntity<PostAuctionByKey> request = new HttpEntity<>(auctionPayload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONArray(response.getBody());
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

    public static record GetCost(
            Integer auc_id
    ) {}
    public JSONObject getCost(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/get-cost";

        GetCost costPayload = new GetCost(auc_id);
        HttpEntity<GetCost> request = new HttpEntity<>(costPayload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return  new JSONObject(response.getBody());
    }

    public String newPayment(ProxyController.NewPaymentRequest payload) {
        String url = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/new-payment";

        HttpEntity<ProxyController.NewPaymentRequest> request = new HttpEntity<>(payload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record GetReceipt(
            Integer auc_id
    ) {}
    public JSONObject getReceipt(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/get-receipt";

        GetReceipt receiptPayload = new GetReceipt(auc_id);
        HttpEntity<GetReceipt> request = new HttpEntity<>(receiptPayload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONObject(response.getBody());
    }

    public String signUp(User user) {
        String url = "http://localhost:" + env.getProperty("userServer.port") + "/api/users/sign-up";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("userServer.apiKey"));
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record GetUserDetails(
            String usr_username
    ) {}
    public UserDetails getUserDetails(String username) {
        String url = "http://localhost:" + env.getProperty("userServer.port") + "/api/users/get-user-details";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("userServer.apiKey"));
        GetUserDetails userDetailsPayload = new GetUserDetails(username);
        HttpEntity<GetUserDetails> request = new HttpEntity<>(userDetailsPayload, headers);

        ResponseEntity<UserDetails> response = this.restTemplate.postForEntity(url, request, UserDetails.class); //ToDO: Try catch

        return response.getBody();
    }

    public String resetData() {
        String auctionUrl = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/reset-auction-data";
        String itemUrl = "http://localhost:" + env.getProperty("itemServer.port") + "/api/items/reset-item-data";
        String paymentUrl = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/reset-payment-data";

        this.restTemplate.postForEntity(auctionUrl, null, String.class); //ToDO: Try catch
        this.restTemplate.postForEntity(itemUrl, null, String.class);
        this.restTemplate.postForEntity(paymentUrl, null, String.class);

        return "Data reset";
    }

}
