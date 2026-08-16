package com.ada_hd.order_management_service.domain.entity;

// Maps to order_cancellations

import com.ada_hd.order_management_service.domain.enums.ActorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "order_cancellations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCancellation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// unique = true: an order can only be cancelled ONCE — matches the state
	// machine (CANCELLED has no transition that loops back to allow a
	// second cancellation).
	@Column(name = "order_id", nullable = false, unique = true)
	private Long orderId;

	@Enumerated(EnumType.STRING)
	@Column(name = "cancelled_by_type", nullable = false)
	private ActorType cancelledByType;

	@Column(name = "cancelled_by_id")
	private Long cancelledById;

	@Column(name = "reason_code", nullable = false)
	private String reasonCode;

	// Whether this cancellation requires money to be sent back — false when
	// the customer cancels BEFORE paying.
	@Column(name = "refund_required", nullable = false)
	@Builder.Default
	private Boolean refundRequired = false;

	@Column(name = "cancelled_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime cancelledAt;

}
