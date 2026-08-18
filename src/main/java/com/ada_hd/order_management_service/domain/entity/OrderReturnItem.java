package com.ada_hd.order_management_service.domain.entity;

// Maps to order_return_items — one row per item covered by a return request
// (see OrderReturnRequest). orderId/rmaCode/status/reason/timestamps all live
// on the header now; this row only holds what's specific to THIS item within
// THAT request.

import com.ada_hd.order_management_service.domain.enums.ItemCondition;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
				name = "order_return_items",
				uniqueConstraints = @UniqueConstraint(
								name = "uk_return_request_item",
								columnNames = {"return_request_id", "order_item_id"}
				)
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReturnItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "return_request_id", nullable = false)
	private Long orderReturnRequestId;

	@Column(name = "order_item_id", nullable = false)
	private Long orderItemId;

	@Column(name = "quantity_returned", nullable = false)
	private Integer quantityReturned;

	@Enumerated(EnumType.STRING)
	@Column(name = "condition", nullable = false)
	private ItemCondition condition;

	@Column(name = "refund_amount", nullable = false)
	@Builder.Default
	private BigDecimal refundAmount = BigDecimal.ZERO;
}
