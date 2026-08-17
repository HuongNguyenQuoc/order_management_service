package com.ada_hd.order_management_service.domain.entity;

import com.ada_hd.order_management_service.domain.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "order_return_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReturnRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

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

	// Carrier tracking number for the item(s) shipped back to the seller.
	@Column(name = "return_tracking_number")
	private String returnTrackingNumber;

	@Column(name = "requested_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime requestedAt;

	@Column(name = "approved_at")
	private OffsetDateTime approvedAt;

	@Column(name = "resolved_at")
	private OffsetDateTime resolvedAt;

}
