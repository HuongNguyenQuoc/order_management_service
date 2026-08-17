package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
}
