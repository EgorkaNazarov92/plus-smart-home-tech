package ru.yandex.practicum.handler;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;

@Component
@RequiredArgsConstructor
public abstract class HubEventHandler<T extends SpecificRecordBase> {
	private final KafkaConfig config;
	private final KafkaEventProducer producer;
	private static final String HUB_TOPIC = "telemetry.hubs.v1";

	protected abstract T mapToAvro(HubEvent event);

	public void handle(HubEvent event) {
		T avroObj = mapToAvro(event);
		HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
				.setHubId(event.getHubId())
				.setTimestamp(event.getTimestamp())
				.setPayload(avroObj)
				.build();
		producer.send(HUB_TOPIC, hubEventAvro);
	}
}
