package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderReturnItemRepository extends JpaRepository<OrderReturnItem, Long> {
	// An order can have many returns over its lifetime (one per item) — see
	// project_return_refund_cardinality memory. List, not Optional.
	List<OrderReturnItem> findByOrderId(Long orderId);

	// orderItemId is unique = true: at most one return per item, ever.
	Optional<OrderReturnItem> findByOrderItemId(Long orderItemId);

	Optional<OrderReturnItem> findByRmaCode(String rmaCode);
}
