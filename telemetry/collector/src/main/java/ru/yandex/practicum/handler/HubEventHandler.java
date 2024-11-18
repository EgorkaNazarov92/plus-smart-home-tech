package ru.yandex.practicum.handler;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public abstract class HubEventHandler<T extends SpecificRecordBase> {
	private final KafkaConfig config;
	private final KafkaEventProducer producer;
	private static final String HUB_TOPIC = "telemetry.hubs.v1";

	protected abstract T mapToAvro(HubEventProto event);

	public void handle(HubEventProto event) {
		T avroObj = mapToAvro(event);
		HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
				.setHubId(event.getHubId())
				.setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds()
						, event.getTimestamp().getNanos()))
				.setPayload(avroObj)
				.build();
		producer.send(HUB_TOPIC, hubEventAvro);
	}
}
