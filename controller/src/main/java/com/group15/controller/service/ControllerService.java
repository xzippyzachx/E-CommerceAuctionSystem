package com.group15.controller.service;

import com.group15.controller.api.ProxyController;
import com.group15.controller.bean.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Objects;

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

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        GetAuction auctionPayload = new GetAuction(auc_id);
        HttpEntity<GetAuction> request = new HttpEntity<>(auctionPayload, headers);

        ResponseEntity<String> auctionResponse = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return  new JSONObject(auctionResponse.getBody());
    }

    public JSONArray getAllAuctions() {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-all-auctions";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        HttpEntity<GetAuction> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class); //ToDO: Try catch

        return new JSONArray(response.getBody());
    }

    public static record PostAuctionByKey(
            String keyword
    ) {}
    public JSONArray getAuctionsByKey(String keyword) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-auctions-by-key";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        PostAuctionByKey auctionPayload = new PostAuctionByKey(keyword);
        HttpEntity<PostAuctionByKey> request = new HttpEntity<>(auctionPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONArray(response.getBody());
    }

    public static record PostBid(
            Integer auc_id,
            Double bid_amount,
            Integer usr_id
    ) {}
    public String newBid(Integer auc_id, Double bid_amount, Integer usr_id) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/new-bid";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        PostBid bidPayload = new PostBid(auc_id, bid_amount, usr_id);
        HttpEntity<PostBid> request = new HttpEntity<>(bidPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record GetCost(
            Integer auc_id
    ) {}
    public JSONObject getCost(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/get-cost";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("paymentServer.apiKey"));
        GetCost costPayload = new GetCost(auc_id);
        HttpEntity<GetCost> request = new HttpEntity<>(costPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return  new JSONObject(response.getBody());
    }

    public String newPayment(ProxyController.NewPaymentRequest payload) {
        String url = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/new-payment";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("paymentServer.apiKey"));
        HttpEntity<ProxyController.NewPaymentRequest> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record GetReceipt(
            Integer auc_id
    ) {}
    public JSONObject getReceipt(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/get-receipt";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("paymentServer.apiKey"));
        GetReceipt receiptPayload = new GetReceipt(auc_id);
        HttpEntity<GetReceipt> request = new HttpEntity<>(receiptPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        if(Objects.equals(response.getBody(), "This auction is not paid"))
            return null;

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

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        JSONObject responseJSON = new JSONObject(response.getBody());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(responseJSON.getString("username"), responseJSON.getString("password"), new ArrayList<>());

        return userDetails;
    }

    public String resetData() {
        String auctionUrl = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/reset-auction-data";
        String itemUrl = "http://localhost:" + env.getProperty("itemServer.port") + "/api/items/reset-item-data";
        String paymentUrl = "http://localhost:" + env.getProperty("paymentServer.port") + "/api/payments/reset-payment-data";

        HttpHeaders auctionHeaders = new HttpHeaders();
        auctionHeaders.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        HttpEntity<PostBid> auctionRequest = new HttpEntity<>(auctionHeaders);

        HttpHeaders itemHeaders = new HttpHeaders();
        itemHeaders.add("x-api-key", env.getProperty("itemServer.apiKey"));
        HttpEntity<PostBid> itemRequest = new HttpEntity<>(itemHeaders);

        HttpHeaders paymentHeaders = new HttpHeaders();
        paymentHeaders.add("x-api-key", env.getProperty("paymentServer.apiKey"));
        HttpEntity<PostBid> paymentRequest = new HttpEntity<>(paymentHeaders);

        this.restTemplate.postForEntity(auctionUrl, auctionRequest, String.class); //ToDO: Try catch
        this.restTemplate.postForEntity(itemUrl, itemRequest, String.class);
        this.restTemplate.postForEntity(paymentUrl, paymentRequest, String.class);

        return "Data reset";
    }

}
