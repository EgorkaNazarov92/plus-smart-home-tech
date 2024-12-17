package ru.practicum.yandex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.yandex.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findByShoppingCartId(String cartId);
}
