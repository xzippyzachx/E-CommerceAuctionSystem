package com.group15.auction.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="forward_auctions")
@PrimaryKeyJoinColumn(name = "fwd_id")
public class ForwardAuction extends Auction {

    private Date fwd_end_time;

    public ForwardAuction(Integer auc_id, Integer auc_itm_id, Integer auc_pay_id, String auc_type, Double auc_start_price, Double auc_current_price, Date auc_end_time) {
        super(auc_id, auc_itm_id, auc_pay_id, auc_type, auc_start_price, auc_current_price);
        this.fwd_end_time = auc_end_time;
    }

    public ForwardAuction() {}

    public Date getFwd_end_time() {
        return fwd_end_time;
    }

    public void setFwd_end_time(Date auc_end_time) {
        this.fwd_end_time = auc_end_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ForwardAuction that = (ForwardAuction) o;
        return Objects.equals(fwd_end_time, that.fwd_end_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fwd_end_time);
    }

    @Override
    public String toString() {
        return "ForwardAuction{" +
                "fwd_end_time=" + fwd_end_time +
                '}';
    }
}
