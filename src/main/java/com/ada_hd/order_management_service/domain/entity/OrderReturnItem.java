package com.ada_hd.order_management_service.domain.entity;

// Maps to order_return_items. There is intentionally NO separate "return
// request" header table (no OrderReturn) — each row here IS a complete,
// independent return, for exactly one item. rmaCode/status/reason/timestamps
// used to live on a header entity that grouped multiple items into one
// request; that grouping capability was dropped by design, since nothing in
// this service's requirements needs it — every item's return is tracked and
// approved fully independently, with its own code.

import com.ada_hd.order_management_service.domain.enums.ItemCondition;
import com.ada_hd.order_management_service.domain.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "order_return_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReturnItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Denormalized so "list every return under this order" can be queried
	// directly, without joining through OrderItem first.
	@Column(name = "order_id", nullable = false)
	private Long orderId;

	// unique = true: an item can only ever be returned once, globally — this
	// row is that one return, there's no separate event it belongs to.
	@Column(name = "order_item_id", nullable = false, unique = true)
	private Long orderItemId;

	@Column(name = "rma_code", nullable = false, unique = true)
	private String rmaCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	@Builder.Default
	private ReturnStatus status = ReturnStatus.REQUESTED;

	@Column(name = "reason_code", nullable = false)
	private String reasonCode;

	@Column(name = "reason_text")
	private String reasonText;

	@Column(name = "quantity_returned", nullable = false)
	private Integer quantityReturned;

	// Filled in once the item is physically received and inspected — stays
	// null until then.
	@Enumerated(EnumType.STRING)
	@Column(name = "condition")
	private ItemCondition condition;

	@Column(name = "refund_amount", nullable = false)
	@Builder.Default
	private BigDecimal refundAmount = BigDecimal.ZERO;

	@Column(name = "requested_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime requestedAt;

	@Column(name = "resolved_at")
	private OffsetDateTime resolvedAt;
}
