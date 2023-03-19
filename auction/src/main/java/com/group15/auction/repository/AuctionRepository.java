package com.group15.auction.repository;

import com.group15.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    @Query(value="SELECT a FROM Auction a WHERE a.auc_type = :auc_type")
    List<Auction> findByAuctionType(@Param("auc_type") String auc_type);

    @Query(value="SELECT a FROM Auction a ORDER BY a.auc_id")
    List<Auction> findAll();

    @Modifying
    @Transactional
    @Query(value="CALL auction_data_reset()", nativeQuery = true)
    void resetAuctionData();

}
