package com.group15.auction.repository;

import com.group15.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Integer> {

    @Query(value="SELECT b FROM Bid b WHERE b.bid_auc_id.auc_id = :auc_id")
    List<Bid> findByAuction(@Param("auc_id") Integer auc_id);

}
