package com.hrs.paymentservice.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hrs.paymentservice.models.ReservationDto;
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

	@KafkaListener(id = "${consumer.config.reservation.topic.name}", topics = "${consumer.config.reservation.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeRoomReservation(ConsumerRecord<String, ReservationDto> payload) {
		log.info("Topic : {}", reservationTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Reservation : {}", payload.value());

		ReservationDto reservationDto = payload.value();
		paymentService.processTransaction(null);
	}

	@KafkaListener(id = "${consumer.config.cancellation.topic.name}", topics = "${consumer.config.cancellation.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeRoomCancellation(ConsumerRecord<String, ReservationDto> payload) {
		log.info("Topic : {}", cancellationTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Reservation : {}", payload.value());

		ReservationDto reservationDto = payload.value();
		paymentService.processTransaction(null);
	}

}