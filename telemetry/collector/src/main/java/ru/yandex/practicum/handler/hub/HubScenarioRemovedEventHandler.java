package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;
import ru.yandex.practicum.model.hub.ScenarioRemovedEvent;

@HandlerHubEvent(HubEventType.SCENARIO_REMOVED)
@Component
public class HubScenarioRemovedEventHandler extends HubEventHandler<ScenarioRemovedEventAvro> {

	public HubScenarioRemovedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected ScenarioRemovedEventAvro mapToAvro(HubEvent event) {
		ScenarioRemovedEvent removedEvent = (ScenarioRemovedEvent) event;
		return ScenarioRemovedEventAvro.newBuilder()
				.setName(removedEvent.getName())
				.build();
	}
}
