package com.ada_hd.order_management_service.repository;

import com.ada_hd.order_management_service.domain.entity.OrderStatusTransitionRule;
import com.ada_hd.order_management_service.domain.entity.OrderStatusTransitionRuleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusTransitionRuleRepository extends JpaRepository<OrderStatusTransitionRule, OrderStatusTransitionRuleId> {
}
