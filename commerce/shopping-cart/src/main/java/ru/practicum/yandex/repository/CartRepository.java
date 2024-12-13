package ru.practicum.yandex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.yandex.model.ShoppingCart;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, String> {
	ShoppingCart findByUsername(String username);
}
