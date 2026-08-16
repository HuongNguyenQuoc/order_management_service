package com.ada_hd.order_management_service.domain.entity;

import com.ada_hd.order_management_service.domain.enums.ActorType;

import com.ada_hd.order_management_service.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;

// Append-only audit log — the only place that records WHO changed an order's
// status and WHY (lines 350-360). Rows are only ever inserted, never updated.
@Entity
@Table(name = "order_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	// Nullable: null means "this row was recorded when the order was first
	// created" — there is no "previous status" yet to compare against.
	@Enumerated(EnumType.STRING)
	@Column(name = "from_status")
	private OrderStatus fromStatus;


	@Enumerated(EnumType.STRING)
	@Column(name = "to_status", nullable = false)
	private OrderStatus toStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "changed_by_type", nullable = false)
	private ActorType changedByType;

	// Polymorphic column: means customers.id OR admins.id depending on
	// changedByType, or null when changedByType = SYSTEM. There's no real FK
	// here at the DB level either — one column can't reference two different
	// tables conditionally. Genuine SQL limitation, not a mapping gap.
	@Column(name = "changed_by_id")
	private Long changedById;

	@Column(name = "reason")
	private  String reason;

	// Hibernate converts this Map back and forth with the JSONB column
	// automatically, no manual converter class needed. Map<String,Object>
	// instead of a fixed class because the actual keys differ per
	// transition type (e.g. payment_id vs tracking_code) — there's no single
	// fixed shape here.
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "metadata")
	private Map<String, Object> metadata;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;

}
