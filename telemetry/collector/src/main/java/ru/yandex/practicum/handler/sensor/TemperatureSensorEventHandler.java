package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorEvent;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT)
@Component
public class TemperatureSensorEventHandler extends SensorEventHandler<TemperatureSensorAvro> {
	public TemperatureSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected TemperatureSensorAvro mapToAvro(SensorEventProto event) {
		TemperatureSensorEvent sensorEvent = event.getTemperatureSensorEvent();
		return TemperatureSensorAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setTemperatureF(sensorEvent.getTemperatureF())
				.build();
	}
}
