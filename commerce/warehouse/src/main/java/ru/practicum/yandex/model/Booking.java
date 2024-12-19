package ru.practicum.yandex.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
	@Id
	@Column(name = "order_id")
	private String orderId;

	@Column(name = "fragile")
	private boolean fragile;

	@Column(name = "delivery_volume")
	private double deliveryVolume;

	@Column(name = "delivery_weight")
	private double deliveryWeight;

	@ElementCollection
	@CollectionTable(name = "booking_products",
			joinColumns = @JoinColumn(name = "order_id"))
	@MapKeyColumn(name = "product_id")
	@Column(name = "quantity")
	Map<String, Long> products;

	@Column(name = "delivery_id")
	private String deliveryId;

}
