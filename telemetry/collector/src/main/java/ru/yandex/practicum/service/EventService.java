package ru.yandex.practicum.service;

import ru.yandex.practicum.model.sensor.SensorEvent;

public interface EventService {
	void addSensorEvent(SensorEvent sensorEvent);
}
