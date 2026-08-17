package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
	// We use List here because an order can have multiple status history entries, and we want to retrieve them all.
	List<OrderStatusHistory> findByOrderIdOrderByCreatedAtDesc(Long orderId);
}
