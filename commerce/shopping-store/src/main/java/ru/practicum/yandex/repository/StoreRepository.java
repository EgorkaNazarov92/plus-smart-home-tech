package ru.practicum.yandex.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.yandex.model.Product;
import ru.yandex.practicum.types.ProductCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Product, String> {
	List<Product> findAllByProductCategory(ProductCategory productCategory, Pageable pageable);

	Optional<Product> getByProductId(String productId);
}
