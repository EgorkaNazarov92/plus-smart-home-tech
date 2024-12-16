package ru.practicum.yandex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.yandex.model.WarehouseProduct;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseProduct, String> {
	Optional<WarehouseProduct> getByProductId(String productId);
}
