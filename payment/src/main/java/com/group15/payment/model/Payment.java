package com.group15.payment.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @SequenceGenerator(
            name = "pay_id_sequence",
            sequenceName = "pay_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pay_id_sequence"
    )
    private Integer pay_id;
    private Integer pay_usr_id;
    private Double pay_amount;
    private Long pay_card_number;
    private String pay_person_name;
    private Date pay_expiry_date;
    private Integer pay_security_code;

    public Payment() {
    }

    public Payment(Integer pay_id, Integer pay_usr_id, Double pay_amount, Long pay_card_number, String pay_person_name, Date pay_expiry_date, Integer pay_security_code) {
        this.pay_id = pay_id;
        this.pay_usr_id = pay_usr_id;
        this.pay_amount = pay_amount;
        this.pay_card_number = pay_card_number;
        this.pay_person_name = pay_person_name;
        this.pay_expiry_date = pay_expiry_date;
        this.pay_security_code = pay_security_code;
    }

    public Integer getPay_id() {
        return pay_id;
    }

    public void setPay_id(Integer pay_id) {
        this.pay_id = pay_id;
    }

    public Integer getPay_usr_id() {
        return pay_usr_id;
    }

    public void setPay_usr_id(Integer pay_usr_id) {
        this.pay_usr_id = pay_usr_id;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Payment)) return false;
        if (!super.equals(object)) return false;
        Payment payment = (Payment) object;
        return java.util.Objects.equals(getPay_id(), payment.getPay_id()) && java.util.Objects.equals(getPay_usr_id(), payment.getPay_usr_id()) && java.util.Objects.equals(getPay_amount(), payment.getPay_amount()) && java.util.Objects.equals(getPay_card_number(), payment.getPay_card_number()) && java.util.Objects.equals(getPay_person_name(), payment.getPay_person_name()) && java.util.Objects.equals(getPay_expiry_date(), payment.getPay_expiry_date()) && java.util.Objects.equals(getPay_security_code(), payment.getPay_security_code());
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), getPay_id(), getPay_usr_id(), getPay_amount(), getPay_card_number(), getPay_person_name(), getPay_expiry_date(), getPay_security_code());
    }

    public Double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(Double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public Long getPay_card_number() {
        return pay_card_number;
    }

    public void setPay_card_number(Long pay_card_number) {
        this.pay_card_number = pay_card_number;
    }

    public String getPay_person_name() {
        return pay_person_name;
    }

    public void setPay_person_name(String pay_person_name) {
        this.pay_person_name = pay_person_name;
    }

    public Date getPay_expiry_date() {
        return pay_expiry_date;
    }

    public void setPay_expiry_date(Date pay_expiry_date) {
        this.pay_expiry_date = pay_expiry_date;
    }

    public Integer getPay_security_code() {
        return pay_security_code;
    }

    public void setPay_security_code(Integer pay_security_code) {
        this.pay_security_code = pay_security_code;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Payment{" +
                "pay_id=" + pay_id +
                ", pay_usr_id=" + pay_usr_id +
                ", pay_amount=" + pay_amount +
                ", pay_card_number=" + pay_card_number +
                ", pay_person_name='" + pay_person_name + '\'' +
                ", pay_expiry_date=" + pay_expiry_date +
                ", pay_security_code=" + pay_security_code +
                '}';
    }
}