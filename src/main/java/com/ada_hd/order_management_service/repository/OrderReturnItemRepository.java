package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderReturnItemRepository extends JpaRepository<OrderReturnItem, Long> {
	List<OrderReturnItem> findByOrderReturnRequestId(Long orderReturnRequestId);

	// Not unique anymore — the same item can be covered by more than one
	// return request over its lifetime.
	List<OrderReturnItem> findByOrderItemId(Long orderItemId);
}
