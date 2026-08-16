package com.ada_hd.order_management_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;

// Transactional outbox pattern (lines 776-816): a row is written HERE in the
// SAME transaction as the business change (e.g. status -> CONFIRMED). A
// separate background worker later polls published=false rows and actually
// publishes them to Kafka — guaranteeing the DB write and the "event to
// publish" never fall out of sync, even if the process crashes in between.
@Entity
@Table(name = "order_outbox_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderOutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Column(name = "event_type", nullable = false)
	private String eventType;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "payload", nullable = false)
	private Map<String, Object> payload;

	@Column(name = "published", nullable = false)
	@Builder.Default
	private Boolean published = false;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;
}
