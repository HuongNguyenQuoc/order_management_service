package com.ada_hd.order_management_service.domain.entity;

// Maps to order_refunds — the actual money-movement transaction, separate
// from order_cancellation_items/order_return_items because "refund owed" is
// a different fact from "refund approved and money actually sent", which
// needs its own approval workflow.
// This is deliberately item-level, not order-level: sourceId points at ONE
// OrderReturnItem or OrderCancellationItem row (never at "the order" as a
// whole), so amount always reflects a single product's refund, never an
// aggregate across the order. order_id is NOT unique: an order can have
// many items, each producing its own refund. The (source_type, source_id)
// pair is what's actually unique — it stops the same return-item or
// cancellation-item from ever generating two refund rows.

import com.ada_hd.order_management_service.domain.enums.ActorType;
import com.ada_hd.order_management_service.domain.enums.RefundSourceType;
import com.ada_hd.order_management_service.domain.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
		name = "order_refunds",
		uniqueConstraints = @UniqueConstraint(columnNames = {"source_type", "source_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRefund {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Enumerated(EnumType.STRING)
	@Column(name = "source_type", nullable = false)
	private RefundSourceType sourceType;

	// Polymorphic: points to order_cancellation_items.id OR
	// order_return_items.id depending on sourceType — i.e. one specific
	// product's return/cancellation, never the whole order. There's no real
	// FK possible here at the DB level — one column can't reference two
	// different tables conditionally.
	@Column(name = "source_id", nullable = false)
	private Long sourceId;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "method")
	private String method;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	@Builder.Default
	private RefundStatus status = RefundStatus.PENDING_APPROVAL;

	@Enumerated(EnumType.STRING)
	@Column(name = "requested_by_type")
	private ActorType requestedByType;

	@Column(name = "requested_by_id")
	private Long requestedById;

	// Could technically have a real FK to admins(id) — only staff approve a
	// refund, so there's exactly one possible target table, no polymorphism
	// here. Kept as a plain Long anyway, to stay consistent with "no
	// cross-module JPA relations" elsewhere in this service.
	@Column(name = "approved_by_id")
	private Long approvedById;

	@Column(name = "approved_at")
	private OffsetDateTime approvedAt;

	@Column(name = "rejected_reason")
	private String rejectedReason;

	@Column(name = "processed_at")
	private OffsetDateTime processedAt;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;
}
