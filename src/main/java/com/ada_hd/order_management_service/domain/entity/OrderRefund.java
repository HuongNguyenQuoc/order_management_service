package com.ada_hd.order_management_service.domain.entity;

// Maps to order_refunds — the actual money-movement transaction, separate
// from order_return_requests/order_cancellations because "refund owed" is
// a different fact from "refund approved and money actually sent", which
// needs its own approval workflow.
// sourceId points at ONE OrderReturnRequest or OrderCancellation row — one
// return event or one order-cancellation event, depending on sourceType.

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

	/// Polymorphic: points to order_return_requests.id when sourceType = RETURN,
// or order_cancellations.id when sourceType = CANCELLATION. There's no real
// FK possible here at the DB level — one column can't reference two
// different tables conditionally.
	@Column(name = "source_id", nullable = false)
	private Long sourceId;

	// The amount ACTUALLY processed for this refund transaction. Distinct
	// from OrderReturnItem.refundAmount / OrderCancellationItem.refundAmount
	// (the owed/estimated amount at approval time) — this can differ in
	// practice (e.g. a processing fee deducted, or a partial payout), and
	// only exists once a refund has actually been requested/approved.
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
