package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderCancellationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderCancellationItemRepository extends JpaRepository<OrderCancellationItem, Long> {
	List<OrderCancellationItem> findByOrderId(Long orderId);

	Optional<OrderCancellationItem> findByOrderItemId(Long orderItemId);

	Optional<OrderCancellationItem> findByCancellationCode(String orderCancellationCode);

}
