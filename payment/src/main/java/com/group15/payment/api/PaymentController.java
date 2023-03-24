package com.group15.payment.api;

import com.group15.payment.model.Payment;
import com.group15.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("api/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    static record NewPaymentRequest(
            Integer auc_id,
            Double pay_amount,
            Long pay_card_number,
            String pay_person_name,
            Date pay_expiry_date,
            Integer pay_security_code,
            Boolean expedited_shipping

    ) {}
    @PostMapping("new-payment")
    public String getNewPayment(@RequestBody NewPaymentRequest request) {
        return paymentService.createNewPayment(request.auc_id, request.pay_amount, request.pay_card_number, request.pay_person_name, request.pay_expiry_date, request.pay_security_code, request.expedited_shipping);
    }

    static record GetReceiptRequest(
            Integer auc_id
    ) {}
    @RequestMapping(value = "get-receipt", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
    public String getReceipt(@RequestBody GetReceiptRequest request) {
        return paymentService.getPaymentReceipt(request.auc_id);
    }

    static record GetCostRequest(
            Integer auc_id
    ) {}
    @RequestMapping(value = "get-cost", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
    public String getCost(@RequestBody GetCostRequest request) {
        return paymentService.getCost(request.auc_id).toString();
    }

    @PostMapping("reset-payment-data")
    public String resetPaymentData() {
        paymentService.resetPaymentData();
        return "Payment data reset";
    }
}



