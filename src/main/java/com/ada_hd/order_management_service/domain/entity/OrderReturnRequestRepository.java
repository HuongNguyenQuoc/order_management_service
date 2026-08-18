package com.ada_hd.order_management_service.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderReturnRequestRepository extends JpaRepository<OrderReturnRequest, Long> {

	List<OrderReturnRequest> findByOrderId(Long orderId);
	Optional<OrderReturnRequest> findByRmaCode(String rmaCode);
}
