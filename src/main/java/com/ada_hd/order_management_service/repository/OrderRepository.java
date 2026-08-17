package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findByOrderCode(String orderCode);
}
