package com.ada_hd.order_management_service.domain.entity;

// Each row is one allowed status transition, e.g. PENDING -> CONFIRMED.
// This table is the single source of truth for allowed status transitions, and can be used to validate status changes in the Order entity.

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "order_status_transitions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusTransitionRule {

	@EmbeddedId
	private OrderStatusTransitionRuleId id;
}
