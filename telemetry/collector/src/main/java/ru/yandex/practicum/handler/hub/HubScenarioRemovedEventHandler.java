package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@HandlerHubEvent(HubEventProto.PayloadCase.SCENARIO_REMOVED)
@Component
public class HubScenarioRemovedEventHandler extends HubEventHandler<ScenarioRemovedEventAvro> {

	public HubScenarioRemovedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected ScenarioRemovedEventAvro mapToAvro(HubEventProto event) {
		ScenarioRemovedEventProto removedEvent = event.getScenarioRemoved();
		return ScenarioRemovedEventAvro.newBuilder()
				.setName(removedEvent.getName())
				.build();
	}
}
