package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.Order;
import com.ada_hd.order_management_service.domain.entity.OrderCancellation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderCancellationRepository extends JpaRepository<OrderCancellation, Long> {

	Optional<OrderCancellation> findByOrderId(Long orderId);
	Optional<OrderCancellation> findByCancellationCode(String cancellationCode);
}
