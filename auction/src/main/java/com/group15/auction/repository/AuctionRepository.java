package com.group15.auction.repository;

import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    @Query(value="SELECT a FROM Auction a WHERE a.auc_type = :auc_type")
    List<Auction> findByAuctionType(@Param("auc_type") String auc_type);

}
