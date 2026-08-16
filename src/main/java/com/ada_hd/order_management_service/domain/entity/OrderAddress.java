package com.ada_hd.order_management_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;


// Maps to order_addresses
@Entity
@Table(name = "order_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "recipient_name", nullable = false)
  private String recipientName;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "line1", nullable = false)
  private String line1;

  @Column(name = "line2")
  private String line2;

  @Column(name = "ward")
  private String ward;

  @Column(name = "district")
  private String district;

	@Column(name = "province", nullable = false)
	private String province;

	// DB default is "VN"
	@Column(name = "country_code", nullable = false)
	@Builder.Default
	private String countryCode = "VN";

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "source_address_id")
	private  Long sourceAddressId;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;

}