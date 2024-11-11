package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.HandlerSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.model.sensor.SwitchSensorEvent;

@HandlerSensorEvent(SensorEventType.SWITCH_SENSOR_EVENT)
@Component
public class SwitchSensorEventHandler extends SensorEventHandler<SwitchSensorAvro> {
	public SwitchSensorEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected SwitchSensorAvro mapToAvro(SensorEvent event) {
		SwitchSensorEvent sensorEvent = (SwitchSensorEvent) event;
		return SwitchSensorAvro.newBuilder()
				.setState(sensorEvent.getState())
				.build();
	}
}
