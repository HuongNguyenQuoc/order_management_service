package com.ada_hd.order_management_service.domain.entity;

// Maps to order_cancellations. Cancellation is order-level, not per-item —
// unlike returns, a cancellation only ever happens BEFORE fulfillment, when
// the order hasn't been split/picked per item yet, so it always applies to
// the whole order at once. order_id is unique: at most one cancellation per
// order, ever. Replaces the old per-item OrderCancellationItem design.

import com.ada_hd.order_management_service.domain.enums.ActorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
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

	@Column(name = "order_id", nullable = false, unique = true)
	private Long orderId;

	@Column(name = "cancellation_code", nullable = false, unique = true)
	private String cancellationCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "cancelled_by_type", nullable = false)
	private ActorType cancelledByType;

	@Column(name = "cancelled_by_id")
	private Long cancelledById;

	@Column(name = "reason_code", nullable = false)
	private String reasonCode;

	@Column(name = "reason_text")
	private String reasonText;

	// Whether this cancellation requires money to be sent back — false when
	// the customer cancels BEFORE paying.
	@Column(name = "refund_required", nullable = false)
	@Builder.Default
	private Boolean refundRequired = false;

	@Column(name = "refund_amount", nullable = false)
	@Builder.Default
	private BigDecimal refundAmount = BigDecimal.ZERO;

	@Column(name = "cancelled_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime cancelledAt;

}
