package ru.practicum.yandex.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "dimension_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dimension {
	@Id
	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "width", nullable = false)
	private double width;

	@Column(name = "height", nullable = false)
	private double height;

	@Column(name = "depth", nullable = false)
	private double depth;
}
