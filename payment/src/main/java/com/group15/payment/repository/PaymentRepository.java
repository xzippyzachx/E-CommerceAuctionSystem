package com.group15.payment.repository;


import com.group15.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Modifying
    @Transactional
    @Query(value="CALL payment_data_reset()", nativeQuery = true)
    void resetPaymentData();

}