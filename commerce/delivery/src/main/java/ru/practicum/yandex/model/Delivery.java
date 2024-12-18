package ru.practicum.yandex.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.types.DeliveryState;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "delivery_id", nullable = false)
	private String deliveryId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_address_id", referencedColumnName = "address_id")
	private Address fromAddress;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_address_id", referencedColumnName = "address_id")
	private Address toAddress;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "delivery_state")
	@Enumerated(EnumType.STRING)
	private DeliveryState deliveryState;
}
