package com.group15.auction.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="bids")
public class Bid {

    @Id
    @SequenceGenerator(
            name = "bid_id_sequence",
            sequenceName = "bid_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bid_id_sequence"
    )
    private Integer bid_id;
    private Integer bid_usr_id;
    @ManyToOne
    @JoinColumn(name = "bid_auc_id")
    private Auction bid_auc_id;
    private Date bid_time;
    private Double bid_amount;

    public Bid(Integer bid_id, Integer bid_usr_id, Auction bid_auc_id, Date bid_time, Double bid_amount) {
        this.bid_id = bid_id;
        this.bid_usr_id = bid_usr_id;
        this.bid_auc_id = bid_auc_id;
        this.bid_time = bid_time;
        this.bid_amount = bid_amount;
    }

    public Bid() {}

    public Integer getBid_id() {
        return bid_id;
    }

    public void setBid_id(Integer bid_id) {
        this.bid_id = bid_id;
    }

    public Integer getBid_usr_id() {
        return bid_usr_id;
    }

    public void setBid_usr_id(Integer bid_usr_id) {
        this.bid_usr_id = bid_usr_id;
    }

    public Auction getBid_auc_id() {
        return bid_auc_id;
    }

    public void setBid_auc_id(Auction bid_auc_id) {
        this.bid_auc_id = bid_auc_id;
    }

    public Date getBid_time() {
        return bid_time;
    }

    public void setBid_time(Date bid_time) {
        this.bid_time = bid_time;
    }

    public Double getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(Double bid_amount) {
        this.bid_amount = bid_amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return bid_id.equals(bid.bid_id) && Objects.equals(bid_usr_id, bid.bid_usr_id) && Objects.equals(bid_auc_id, bid.bid_auc_id) && Objects.equals(bid_time, bid.bid_time) && Objects.equals(bid_amount, bid.bid_amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bid_id, bid_usr_id, bid_auc_id, bid_time, bid_amount);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bid_id=" + bid_id +
                ", bid_usr_id=" + bid_usr_id +
                ", bid_auc_id=" + bid_auc_id +
                ", bid_time=" + bid_time +
                ", bid_amount=" + bid_amount +
                '}';
    }
}
