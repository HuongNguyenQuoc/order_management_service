package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderOutboxEventRepository extends JpaRepository<OrderOutboxEvent, Long> {
	// Polled by the outbox publisher job to find events not yet sent to Kafka.
	List<OrderOutboxEvent> findByPublishedFalse();
}
