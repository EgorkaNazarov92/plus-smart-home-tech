package ru.practicum.yandex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouse_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseProduct {
	@Id
	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "fragile", nullable = false)
	private boolean fragile;

	@Column(name = "weight", nullable = false)
	private double weight;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Dimension dimension;
}
