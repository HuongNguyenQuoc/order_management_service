package com.ada_hd.order_management_service.domain.entity;

// Maps to order_items table in the database
// Snapshot of product name/price at purchase time
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Foreign key to the orders table
	// Many-to-one relationship with Order entity
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	// Plain Long - products table belongs to a different module
	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(name = "product_sku", nullable = false)
	private String productSku;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "variant_name")
	private String variantName;

	// Price at the time of purchase, not the current price in the products table
	@Column(name = "unit_price", nullable = false)
	private BigDecimal unitPrice;

	@Column(name = "quantity", nullable = false)
	private  Integer quantity;

	@Column(name = "discount_amount", nullable = false)
	@Builder.Default
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Column(name = "tax_amount", nullable = false)
	@Builder.Default
	private BigDecimal taxAmount = BigDecimal.ZERO;

	// Total price for this item (unit price * quantity - discount + tax)
	// This column is GENERATED ALWAYS AS (...) STORED in Postgres, so it's
	// computed by the database itself. insertable = false AND updatable = false
	// are both required — with only updatable = false, Hibernate would still
	// send a value on INSERT and Postgres would reject the statement outright.
	@Column(name = "line_total", insertable = false, updatable = false)
	private BigDecimal lineTotal;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;

}
