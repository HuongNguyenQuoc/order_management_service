package com.ada_hd.order_management_service.domain.entity;

// order_status_transition has a composite primary key consisting of from_status and to_status.
// JPA does not support composite primary keys directly, so we create a separate class to represent the composite key.
// So the pair is wrapped in its own @Embeddable class.

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderStatusTransitionRuleId implements Serializable {
		@Column(name = "from_status")
		private String fromStatus;

		@Column(name = "to_status")
		private String toStatus;
}
