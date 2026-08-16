package com.ada_hd.order_management_service.domain.entity;

// Maps to orders - root of the state machine. Every other
// table in this module points to this table.

import com.ada_hd.order_management_service.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Pulic lookup code (e.g. ORD-20260812-0001) for external reference. Not the primary key.
	@Column(name = "order_code", nullable = false, unique = true)
	private String orderCode;

	// Plain long - customers table belongs to a different module
	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	// EnumType.STRING is used to store the enum name in the database, rather than its ordinal value.
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	@Builder.Default
	private OrderStatus status = OrderStatus.PENDING;

	@Column(name = "currency", nullable = false)
	@Builder.Default
	private String currency = "VND";

	@Column(name = "subtotal_amount", nullable = false)
	@Builder.Default
	private BigDecimal subtotalAmount = BigDecimal.ZERO;

	@Column(name = "discount_amount", nullable = false)
	@Builder.Default
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Column(name = "shipping_fee", nullable = false)
	@Builder.Default
	private BigDecimal shippingFee = BigDecimal.ZERO;

	@Column(name = "tax_amount", nullable = false)
	@Builder.Default
	private BigDecimal taxAmount = BigDecimal.ZERO;

	@Column(name = "total_amount", nullable = false)
	@Builder.Default
	private BigDecimal totalAmount = BigDecimal.ZERO;

	// Plain Long, not @ManyToOne, because the order_addresses table belongs to a different module
	@Column(name = "shipping_address_id", nullable = false)
	private Long shippingAddressId;

	@Column(name = "billing_address_id")
	private Long billingAddressId;

	@Column(name = "placed_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime placedAt;

	// The time when the order was confirmed by the system or by an admin.
	// This is different from placedAt, which is when the customer placed the order.
	@Column(name = "confirmed_at")
	private OffsetDateTime confirmedAt;

	@Column(name = "shipped_at")
	private OffsetDateTime shippedAt;

	@Column(name = "delivered_at")
	private OffsetDateTime deliveredAt;

	// Version field for optimistic locking with @Version annotation.
	// This field is automatically managed by JPA and is used to detect concurrent modifications to the same entity.
	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private OffsetDateTime updatedAt;

	// One-to-many relationship with OrderItem
	// CascadeType.ALL means that all operations (persist, merge, remove, refresh, detach) will be cascaded to the associated OrderItem entities.
	// orphanRemoval = true means that if an OrderItem is removed from the items list, it will be deleted from the database as well.
	// FetchType.LAZY means that the associated OrderItem entities will be loaded on demand, rather than being loaded immediately with the Order entity.
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@Builder.Default
	private List<OrderItem> items = new ArrayList<>();
}
