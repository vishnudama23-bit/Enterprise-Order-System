
package com.example.paymentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    boolean existsByOrderId(Long orderId);
}
