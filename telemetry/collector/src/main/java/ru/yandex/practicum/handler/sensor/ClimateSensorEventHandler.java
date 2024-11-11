package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;

@HandlerSensorEvent(SensorEventType.CLIMATE_SENSOR_EVENT)
@Component
public class ClimateSensorEventHandler extends SensorEventHandler<ClimateSensorAvro> {
	public ClimateSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected ClimateSensorAvro mapToAvro(SensorEvent event) {
		ClimateSensorEvent sensorEvent = (ClimateSensorEvent) event;
		return ClimateSensorAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setHumidity(sensorEvent.getHumidity())
				.setCo2Level(sensorEvent.getCo2Level())
				.build();
	}
}
