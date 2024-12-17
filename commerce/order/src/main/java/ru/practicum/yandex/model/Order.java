package ru.practicum.yandex.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.types.OrderState;

import java.util.Map;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "order_id", nullable = false)
	private String orderId;

	@Column(name = "shopping_cart_id", nullable = false)
	private String shoppingCartId;


	@ElementCollection
	@CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
	@MapKeyColumn(name = "product_id")
	@Column(name = "quantity")
	private Map<String, Long> products;

	@Column(name = "payment_id", nullable = false)
	private String paymentId;

	@Column(name = "delivery_id", nullable = false)
	private String deliveryId;


	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderState state;

	@Column(name = "delivery_weight", nullable = false)
	private double deliveryWeight;

	@Column(name = "delivery_volume", nullable = false)
	private double deliveryVolume;

	@Column(name = "fragile", nullable = false)
	private boolean fragile;

	@Column(name = "total_price", nullable = false)
	private double totalPrice;

	@Column(name = "delivery_price", nullable = false)
	private double deliveryPrice;

	@Column(name = "product_price", nullable = false)
	private double productPrice;
}
