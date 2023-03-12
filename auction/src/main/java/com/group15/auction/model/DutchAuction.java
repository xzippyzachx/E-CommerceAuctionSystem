package com.group15.auction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="dutch_auctions")
@PrimaryKeyJoinColumn(name = "dch_id")
public class DutchAuction extends Auction {

    private double dch_decrease_amount;
    private Integer dch_decrease_interval;
    private double dch_min_price;
    private Integer dch_end_delay;

    public DutchAuction(Integer auc_id, Integer auc_itm_id, Integer auc_pay_id, String auc_type, Double auc_start_price, Double auc_current_price, double dch_decrease_amount, Integer dch_decrease_interval, double dch_min_price, Integer dch_end_delay) {
        super(auc_id, auc_itm_id, auc_pay_id, auc_type, auc_start_price, auc_current_price);
        this.dch_decrease_amount = dch_decrease_amount;
        this.dch_decrease_interval = dch_decrease_interval;
        this.dch_min_price = dch_min_price;
        this.dch_end_delay = dch_end_delay;
    }

    public DutchAuction() {}

    public double getDch_decrease_amount() {
        return dch_decrease_amount;
    }

    public void setDch_decrease_amount(double dch_decrease_amount) {
        this.dch_decrease_amount = dch_decrease_amount;
    }

    public double getDch_min_price() {
        return dch_min_price;
    }

    public void setDch_min_price(double dch_min_price) {
        this.dch_min_price = dch_min_price;
    }

    public Integer getDch_end_delay() {
        return dch_end_delay;
    }

    public void setDch_end_delay(Integer dch_end_delay) {
        this.dch_end_delay = dch_end_delay;
    }

    public Integer getDch_decrease_interval() {
        return dch_decrease_interval;
    }

    public void setDch_decrease_interval(Integer dch_decrease_interval) {
        this.dch_decrease_interval = dch_decrease_interval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DutchAuction that = (DutchAuction) o;
        return Double.compare(that.dch_decrease_amount, dch_decrease_amount) == 0 && Double.compare(that.dch_min_price, dch_min_price) == 0 && Objects.equals(dch_decrease_interval, that.dch_decrease_interval) && Objects.equals(dch_end_delay, that.dch_end_delay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dch_decrease_amount, dch_decrease_interval, dch_min_price, dch_end_delay);
    }

    @Override
    public String toString() {
        return "DutchAuction{" +
                "dch_decrease_amount=" + dch_decrease_amount +
                ", dch_decrease_interval=" + dch_decrease_interval +
                ", dch_min_price=" + dch_min_price +
                ", dch_end_delay=" + dch_end_delay +
                '}';
    }
}
