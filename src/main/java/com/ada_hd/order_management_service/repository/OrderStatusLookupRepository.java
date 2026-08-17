package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderStatusLookup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusLookupRepository extends JpaRepository<OrderStatusLookup, Long> {
}
