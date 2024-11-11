package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.CommonHandler;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;

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

	@Override
	public void addHubEvent(HubEvent hubEvent) {
		HubEventHandler<? extends SpecificRecordBase> hubEventHandler = handler
				.getHubEventHandlers().get(hubEvent.getType());
		if (hubEventHandler != null)
			hubEventHandler.handle(hubEvent);
		else
			throw new NotFoundException(hubEvent.getType() + " not found");
	}
}
