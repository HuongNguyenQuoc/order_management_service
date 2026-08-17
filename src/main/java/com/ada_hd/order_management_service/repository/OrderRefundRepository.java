package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderRefund;
import com.ada_hd.order_management_service.domain.enums.RefundSourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRefundRepository extends JpaRepository<OrderRefund, Long> {

	List<OrderRefund> findByOrderId(Long orderId);

	// (sourceType, sourceId) is the composite unique constraint — at most one
	// refund per return-item or cancellation-item.
	Optional<OrderRefund> findBySourceTypeAndSourceId(RefundSourceType sourceType, Long sourceId);
}
