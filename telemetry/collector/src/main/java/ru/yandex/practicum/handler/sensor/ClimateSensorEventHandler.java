package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT)
@Component
public class ClimateSensorEventHandler extends SensorEventHandler<ClimateSensorAvro> {
	public ClimateSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected ClimateSensorAvro mapToAvro(SensorEventProto event) {
		ClimateSensorEvent sensorEvent = event.getClimateSensorEvent();
		return ClimateSensorAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setHumidity(sensorEvent.getHumidity())
				.setCo2Level(sensorEvent.getCo2Level())
				.build();
	}
}
