package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.CommonHandler;
import ru.yandex.practicum.model.sensor.SensorEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
	private final CommonHandler handler;

	@Override
	public void addSensorEvent(SensorEvent sensorEvent) {
		SensorEventHandler<? extends SpecificRecordBase> sensorEventHandler = handler
				.getSensorEventHandlers().get(sensorEvent.getType());
		if (sensorEventHandler != null)
			sensorEventHandler.handle(sensorEvent);
		else
			throw new NotFoundException(sensorEvent.getType() + "not found");
	}
}
