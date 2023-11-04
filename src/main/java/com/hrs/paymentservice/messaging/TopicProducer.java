package com.hrs.paymentservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.hrs.paymentservice.models.PaymentDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

	@Value("${producer.config.payment.topic.name}")
	private String paymentTopicName;

	@Value("${producer.config.refund.topic.name}")
	private String refundTopicName;

	private final KafkaTemplate<String, PaymentDto> kafkaTemplate;

	public void sendPayment(PaymentDto payment) {
		log.info("Payment transaction : {}", payment.toString());
		kafkaTemplate.send(paymentTopicName, payment);
	}

	public void sendRefund(PaymentDto payment) {
		log.info("Refund transaction : {}", payment.toString());
		kafkaTemplate.send(refundTopicName, payment);
	}

}