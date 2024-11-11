package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.model.sensor.LightSensorEvent;
import ru.yandex.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;

@HandlerSensorEvent(SensorEventType.MOTION_SENSOR_EVENT)
@Component
public class MotionSensorEventHandler extends SensorEventHandler<MotionSensorAvro> {
	public MotionSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected MotionSensorAvro mapToAvro(SensorEvent event) {
		MotionSensorEvent sensorEvent = (MotionSensorEvent) event;
		return MotionSensorAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setMotion(sensorEvent.getMotion())
				.setVoltage(sensorEvent.getVoltage())
				.build();
	}
}
