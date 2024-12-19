package ru.practicum.yandex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.yandex.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
