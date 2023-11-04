package com.hrs.paymentservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrs.paymentservice.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByCustomerId(Long customerId);

}
