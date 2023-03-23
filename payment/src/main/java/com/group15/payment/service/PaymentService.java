package com.group15.payment.service;

import com.group15.payment.model.Payment;
import com.group15.payment.repository.PaymentRepository;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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

    public String createNewPayment(Integer auc_id, Double pay_amount, Integer pay_card_number, String pay_person_name, Date pay_expiry_date, Integer pay_security_code, Boolean expedited_shipping) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-auction";

        PostAuc auctionPayload = new PostAuc(auc_id);

        HttpEntity<PostAuc> request = new HttpEntity<>(auctionPayload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        JSONObject auction = new JSONObject(response.getBody());

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

        Payment payment = new Payment();
        payment.setPay_amount(total);
        payment.setPay_usr_id(1);
        payment.setPay_card_number(pay_card_number);
        payment.setPay_person_name(pay_person_name);
        payment.setPay_expiry_date(pay_expiry_date);
        payment.setPay_security_code(pay_security_code);
        paymentrepository.save(payment);

        return "Payment Successful";
    }

    public Payment getPaymentReceipt(Integer pay_id) {
        if (paymentrepository.findById(pay_id).isPresent()) {
            return paymentrepository.findById(pay_id).get();
        } else {
            return null;
        }
    }

    public static record PostAuc(
            Integer auc_id
    ) {
    }
    public String getCost(Integer auc_id) {
        String url = "http://localhost:" + env.getProperty("auctionServer.port") + "/api/auctions/get-auction";

        PostAuc aucpayload = new PostAuc(auc_id);

        HttpEntity<PostAuc> request = new HttpEntity<>(aucpayload);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        JSONObject auction = new JSONObject(response.getBody());

        double currentPrice = auction.getDouble("auc_current_price");
        double shippingCost = auction.getJSONObject("auc_itm_id").getDouble("itm_shipping_cost");
        double itmExpeditedCost = auction.getJSONObject("auc_itm_id").getDouble("itm_expedited_cost");

        JSONObject cost = new JSONObject();
        cost.put("auc_current_price", currentPrice);
        cost.put("itm_shipping_cost", shippingCost);
        cost.put("itm_expedited_cost", itmExpeditedCost);

        return cost.toString();
    }
}
