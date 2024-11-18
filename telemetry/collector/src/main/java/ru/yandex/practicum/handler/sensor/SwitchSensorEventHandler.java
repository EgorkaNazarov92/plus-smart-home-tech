package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorEvent;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT)
@Component
public class SwitchSensorEventHandler extends SensorEventHandler<SwitchSensorAvro> {
	public SwitchSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected SwitchSensorAvro mapToAvro(SensorEventProto event) {
		SwitchSensorEvent sensorEvent = event.getSwitchSensorEvent();
		return SwitchSensorAvro.newBuilder()
				.setState(sensorEvent.getState())
				.build();
	}
}
