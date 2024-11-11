package ru.yandex.practicum.handler;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.config.kafka.serializer.EventAvroSerializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.model.sensor.SensorEvent;

@Component
@RequiredArgsConstructor
public abstract class SensorEventHandler<T extends SpecificRecordBase> {
	private final KafkaConfig config;
	private final KafkaEventProducer producer;
	private static final String SENSOR_TOPIC = "telemetry.sensors.v1";

	protected abstract T mapToAvro(SensorEvent event);

	public void handle(SensorEvent event) {
		T avroObj = mapToAvro(event);
		SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
				.setId(event.getId())
				.setHubId(event.getHubId())
				.setTimestamp(event.getTimestamp())
				.setPayload(avroObj)
				.build();
		producer.send(SENSOR_TOPIC, sensorEventAvro);
	}
}
