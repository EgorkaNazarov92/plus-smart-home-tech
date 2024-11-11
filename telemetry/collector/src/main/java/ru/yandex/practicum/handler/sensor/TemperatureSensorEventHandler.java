package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.model.sensor.TemepratureSensorEvent;

@HandlerSensorEvent(SensorEventType.TEMPERATURE_SENSOR_EVENT)
@Component
public class TemperatureSensorEventHandler extends SensorEventHandler<TemperatureSensorAvro> {
	public TemperatureSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected TemperatureSensorAvro mapToAvro(SensorEvent event) {
		TemepratureSensorEvent sensorEvent = (TemepratureSensorEvent) event;
		return TemperatureSensorAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setTemperatureF(sensorEvent.getTemperatureF())
				.build();
	}
}
