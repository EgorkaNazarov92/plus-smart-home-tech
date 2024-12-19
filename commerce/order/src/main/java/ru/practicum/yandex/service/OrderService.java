package ru.practicum.yandex.service;

import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;

import java.util.List;

public interface OrderService {
	List<OrderDto> getUserOrders(String username);

	OrderDto createNewOrder(CreateNewOrderRequest request);

	OrderDto returnOrder(ProductReturnRequest request);

	OrderDto paymentOrder(String orderId);

	OrderDto paymentOrderFailed(String orderId);

	OrderDto deliveryOrder(String orderId);

	OrderDto deliveryOrderFailed(String orderId);

	OrderDto completedOrder(String orderId);

	OrderDto calculateTotalOrder(String orderId);

	OrderDto calculateDeliveryOrder(String orderId);

	OrderDto assemblyOrder(String orderId);

	OrderDto assemblyOrderFailed(String orderId);
}
