package com.group15.payment.service;

import com.group15.payment.model.Payment;
import com.group15.payment.repository.PaymentRepository;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

@Service
public class PaymentService {

    protected final PaymentRepository paymentrepository;
    protected final Environment env;
    protected final RestTemplate restTemplate;


    public PaymentService(PaymentRepository paymentrepository, Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.paymentrepository = paymentrepository;
        this.env = env;
        this.restTemplate = restTemplateBuilder.build();
    }

    public String createNewPayment(Integer auc_id, Double pay_amount, Long pay_card_number, String pay_person_name, Date pay_expiry_date, Integer pay_security_code, Boolean expedited_shipping) {
        JSONObject auction = getAuction(auc_id);

        double currentPrice = auction.getDouble("auc_current_price");
        double shippingCost = auction.getJSONObject("auc_itm_id").getDouble("itm_shipping_cost");
        double itmExpeditedCost = auction.getJSONObject("auc_itm_id").getDouble("itm_expedited_cost");
        double total = currentPrice + shippingCost;
        if (expedited_shipping) {
            total += itmExpeditedCost;
        }

        if (total != pay_amount) {
            return "Pay amount should be " + total;
        }

        JSONObject bestBidUser = getBestBidUser(auc_id);

        Payment payment = new Payment();
        payment.setPay_amount(total);
        payment.setPay_usr_id(bestBidUser.getInt("usr_id"));
        payment.setPay_card_number(pay_card_number);
        payment.setPay_person_name(pay_person_name);
        payment.setPay_expiry_date(pay_expiry_date);
        payment.setPay_security_code(pay_security_code);

        Integer pay_id = paymentrepository.save(payment).getPay_id();

        auctionPaid(auc_id, pay_id);

        return "Payment Successful";
    }

    public String getPaymentReceipt(Integer auc_id) {

        JSONObject auction = getAuction(auc_id);

        if(!auction.has("auc_pay_id"))
            return "This auction is not paid";

        Payment payment = null;
        if(paymentrepository.findById(auction.getInt("auc_pay_id")).isPresent())
            payment = paymentrepository.findById(auction.getInt("auc_pay_id")).get();

        JSONObject payload = getUser(payment.getPay_usr_id());

        payload.put("pay_amount", payment.getPay_amount());

        return payload.toString();
    }

    public static record PostAuc(
            Integer auc_id
    ) {}
    public JSONObject getCost(Integer auc_id) {
        JSONObject auction = getAuction(auc_id);

        double currentPrice = auction.getDouble("auc_current_price");
        double shippingCost = auction.getJSONObject("auc_itm_id").getDouble("itm_shipping_cost");
        double itmExpeditedCost = auction.getJSONObject("auc_itm_id").getDouble("itm_expedited_cost");

        JSONObject cost = new JSONObject();
        if(Objects.equals(auction.getString("auc_state"), "complete")) {
            cost = getBestBidUser(auc_id);
            cost.put("auc_current_price", currentPrice);
            cost.put("itm_shipping_cost", shippingCost);
            cost.put("itm_expedited_cost", itmExpeditedCost);
        }

        return cost;
    }

    public void resetPaymentData() {
        paymentrepository.resetPaymentData();
    }

    private JSONObject getAuction(Integer auc_id) {
        String url = env.getProperty("auctionServer.url") + ":" + env.getProperty("auctionServer.port") + "/api/auctions/get-auction";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        PostAuc auctionPayload = new PostAuc(auc_id);
        HttpEntity<PostAuc> request = new HttpEntity<>(auctionPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONObject(response.getBody());
    }

    public static record PostAuctionPaid(
            Integer auc_id,
            Integer pay_id
    ) {}
    private String auctionPaid(Integer auc_id, Integer pay_id) {
        String url = env.getProperty("auctionServer.url") + ":" + env.getProperty("auctionServer.port") + "/api/auctions/auction-paid";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        PostAuctionPaid auctionPaidPayload = new PostAuctionPaid(auc_id, pay_id);
        HttpEntity<PostAuctionPaid> request = new HttpEntity<>(auctionPaidPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record GetUser(
            Integer usr_id
    ) {}
    private JSONObject getUser(Integer usr_id) {
        String url = env.getProperty("userServer.url") + ":" + env.getProperty("userServer.port") + "/api/users/get-user";

        GetUser userPayload = new GetUser(usr_id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("userServer.apiKey"));
        HttpEntity<GetUser> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONObject(response.getBody());
    }

    public static record GetBestBidUser(
            Integer auc_id
    ) {}
    private JSONObject getBestBidUser(Integer usr_id) {
        String url = env.getProperty("auctionServer.url") + ":" + env.getProperty("auctionServer.port") + "/api/auctions/get-best-bid-user";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("auctionServer.apiKey"));
        GetBestBidUser userPayload = new GetBestBidUser(usr_id);
        HttpEntity<GetBestBidUser> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONObject(response.getBody());
    }
}
