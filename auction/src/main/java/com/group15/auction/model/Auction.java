package com.group15.auction.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="auctions")
public class Auction {

    @Id
    @SequenceGenerator(
            name = "auc_id_sequence",
            sequenceName = "auc_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auc_id_sequence"
    )
    private Integer auc_id;
    private Integer auc_itm_id;
    private Integer auc_pay_id;
    private String auc_type;
    private Double auc_start_price;
    private Double auc_current_price;
    private Date auc_end_time;

    public Auction(Integer auc_id, Integer auc_itm_id, Integer auc_pay_id, String auc_type, Double auc_start_price, Double auc_current_price, Date auc_end_time) {
        this.auc_id = auc_id;
        this.auc_itm_id = auc_itm_id;
        this.auc_pay_id = auc_pay_id;
        this.auc_type = auc_type;
        this.auc_start_price = auc_start_price;
        this.auc_current_price = auc_current_price;
        this.auc_end_time = auc_end_time;
    }

    public Auction(){}

    public Integer getAuc_id() {
        return auc_id;
    }

    public void setAuc_id(Integer auc_id) {
        this.auc_id = auc_id;
    }

    public Integer getAuc_itm_id() {
        return auc_itm_id;
    }

    public void setAuc_itm_id(Integer auc_itm_id) {
        this.auc_itm_id = auc_itm_id;
    }

    public Integer getAuc_pay_id() {
        return auc_pay_id;
    }

    public void setAuc_pay_id(Integer auc_pay_id) {
        this.auc_pay_id = auc_pay_id;
    }

    public String getAuc_type() {
        return auc_type;
    }

    public void setAuc_type(String auc_type) {
        this.auc_type = auc_type;
    }

    public Double getAuc_start_price() {
        return auc_start_price;
    }

    public void setAuc_start_price(Double auc_start_price) {
        this.auc_start_price = auc_start_price;
    }

    public Double getAuc_current_price() {
        return auc_current_price;
    }

    public void setAuc_current_price(Double auc_current_price) {
        this.auc_current_price = auc_current_price;
    }

    public Date getAuc_end_time() {
        return auc_end_time;
    }

    public void setAuc_end_time(Date auc_end_time) {
        this.auc_end_time = auc_end_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return auc_id.equals(auction.auc_id) && Objects.equals(auc_itm_id, auction.auc_itm_id) && Objects.equals(auc_pay_id, auction.auc_pay_id) && Objects.equals(auc_type, auction.auc_type) && Objects.equals(auc_start_price, auction.auc_start_price) && Objects.equals(auc_current_price, auction.auc_current_price) && Objects.equals(auc_end_time, auction.auc_end_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auc_id, auc_itm_id, auc_pay_id, auc_type, auc_start_price, auc_current_price, auc_end_time);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auc_id=" + auc_id +
                ", auc_itm_id=" + auc_itm_id +
                ", auc_pay_id=" + auc_pay_id +
                ", auc_type='" + auc_type + '\'' +
                ", auc_start_price=" + auc_start_price +
                ", auc_current_price=" + auc_current_price +
                ", auc_end_time=" + auc_end_time +
                '}';
    }
}
