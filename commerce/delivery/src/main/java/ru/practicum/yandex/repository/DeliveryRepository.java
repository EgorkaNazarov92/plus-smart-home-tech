package ru.practicum.yandex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.yandex.model.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {
}
