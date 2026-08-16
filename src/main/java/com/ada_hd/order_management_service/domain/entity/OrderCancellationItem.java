package com.ada_hd.order_management_service.domain.entity;

// Maps to order_cancellation_items. Same flattening as OrderReturnItem: no
// separate "cancellation request" header table (no OrderCancellation) —
// each row here IS a complete, independent cancellation, for exactly one
// item, with its own code, reason, and refund handling.

import com.ada_hd.order_management_service.domain.enums.ActorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "order_cancellation_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCancellationItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Denormalized so "list every cancellation under this order" can be
	// queried directly, without joining through OrderItem first.
	@Column(name = "order_id", nullable = false)
	private Long orderId;

	// unique = true: an item can only ever be cancelled once, globally —
	// this row is that one cancellation, there's no separate event it
	// belongs to.
	@Column(name = "order_item_id", nullable = false, unique = true)
	private Long orderItemId;

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

	@Column(name = "quantity_cancelled", nullable = false)
	private Integer quantityCancelled;

	// Whether this cancellation requires money to be sent back — false when
	// the customer cancels BEFORE paying.
	@Column(name = "refund_required", nullable = false)
	@Builder.Default
	private Boolean refundRequired = false;

	// The amount OWED back for this item, computed at cancellation time (only
	// relevant when refundRequired = true). Same distinction as
	// OrderReturnItem.refundAmount: this is a claim/estimate, not a completed
	// transaction — the amount actually sent is tracked in OrderRefund.amount.
	@Column(name = "refund_amount", nullable = false)
	@Builder.Default
	private BigDecimal refundAmount = BigDecimal.ZERO;

	@Column(name = "cancelled_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime cancelledAt;
}
