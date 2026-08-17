package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	// OrderItem holds a @ManyToOne "order" field, not a plain orderId column —
	// "Order_Id" navigates through that relation to order.id.
	List<OrderItem> findByOrderId(Long orderId);
}
