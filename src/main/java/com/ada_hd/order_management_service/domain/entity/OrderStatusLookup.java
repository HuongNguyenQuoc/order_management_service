package com.ada_hd.order_management_service.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

// Maps to order_statuses
//Primary key is String (code), not an auto-generated Long, e.g. "PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"
@Entity
@Table(name = "order_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusLookup {

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "label_vi", nullable = false)
  private String labelVi;

  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder;
}