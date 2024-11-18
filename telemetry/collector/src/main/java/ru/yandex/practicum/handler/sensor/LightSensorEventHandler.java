package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT)
@Component
public class LightSensorEventHandler extends SensorEventHandler<LightSensorAvro> {
	public LightSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected LightSensorAvro mapToAvro(SensorEventProto event) {
		LightSensorEvent sensorEvent = event.getLightSensorEvent();
		return LightSensorAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setLuminosity(sensorEvent.getLuminosity())
				.build();
	}
}
