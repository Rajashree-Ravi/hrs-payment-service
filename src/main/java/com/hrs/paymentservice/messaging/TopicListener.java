package com.hrs.paymentservice.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hrs.paymentservice.gateway.PaymentProviderType;
import com.hrs.paymentservice.models.PaymentDto;
import com.hrs.paymentservice.models.PaymentType;
import com.hrs.paymentservice.models.ReservationDto;
import com.hrs.paymentservice.models.ReservationInfoDto;
import com.hrs.paymentservice.services.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

	@Value("${consumer.config.reservation.topic.name}")
	private String reservationTopicName;

	@Value("${consumer.config.cancellation.topic.name}")
	private String cancellationTopicName;

	@Autowired
	private PaymentService paymentService;

	private final TopicProducer topicProducer;

	@KafkaListener(id = "${consumer.config.reservation.topic.name}", topics = "${consumer.config.reservation.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeRoomReservation(ConsumerRecord<String, ReservationDto> payload) {
		log.info("Topic : {}", reservationTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Reservation : {}", payload.value());

		ReservationDto reservationDto = payload.value();
		ReservationInfoDto reservationInfoDto = reservationDto.getReservationInfoDto();

		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setType(PaymentType.PAY);
		paymentDto.setCustomerId(reservationDto.getCustomerId());
		paymentDto.setReservationId(reservationDto.getId());
		paymentDto.setAmount(reservationInfoDto.getPaymentAmount());
		paymentDto.setProvider(reservationInfoDto.getProvider());

		if (reservationInfoDto.getProvider().equals(PaymentProviderType.CARD)) {
			paymentDto.setCardHolderName(reservationInfoDto.getCardHolderName());
			paymentDto.setCardNumber(reservationInfoDto.getCardNumber());
			paymentDto.setCvv(reservationInfoDto.getCvv());
		} else if (reservationInfoDto.getProvider().equals(PaymentProviderType.UPI)) {
			paymentDto.setVirtualPaymentAddress(reservationInfoDto.getVirtualPaymentAddress());
			paymentDto.setUserComments(reservationInfoDto.getUserComments());
		}

		PaymentDto processedPayment = paymentService.processTransaction(paymentDto);
		topicProducer.sendPayment(processedPayment);
	}

	@KafkaListener(id = "${consumer.config.cancellation.topic.name}", topics = "${consumer.config.cancellation.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeRoomCancellation(ConsumerRecord<String, ReservationDto> payload) {
		log.info("Topic : {}", cancellationTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Reservation : {}", payload.value());

		ReservationDto reservationDto = payload.value();

		PaymentDto paymentDto = paymentService.getPaymentById(reservationDto.getPaymentId());
		paymentDto.setType(PaymentType.REFUND);

		PaymentDto processedPayment = paymentService.processTransaction(paymentDto);
		topicProducer.sendRefund(processedPayment);
	}

}