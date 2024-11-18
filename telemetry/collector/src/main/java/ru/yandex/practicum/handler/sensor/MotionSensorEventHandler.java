package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT)
@Component
public class MotionSensorEventHandler extends SensorEventHandler<MotionSensorAvro> {
	public MotionSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected MotionSensorAvro mapToAvro(SensorEventProto event) {
		MotionSensorEvent sensorEvent = event.getMotionSensorEvent();
		return MotionSensorAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setMotion(sensorEvent.getMotion())
				.setVoltage(sensorEvent.getVoltage())
				.build();
	}
}
