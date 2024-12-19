package ru.practicum.yandex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.yandex.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
