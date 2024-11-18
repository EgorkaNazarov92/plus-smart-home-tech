package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;

import java.util.List;

@HandlerHubEvent(HubEventType.SCENARIO_ADDED)
@Component
public class HubScenarioAddedEventHandler extends HubEventHandler<ScenarioAddedEventAvro> {

	public HubScenarioAddedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected ScenarioAddedEventAvro mapToAvro(HubEvent event) {
		ScenarioAddedEvent hubEvent = (ScenarioAddedEvent) event;
		return ScenarioAddedEventAvro.newBuilder()
				.setName(hubEvent.getName())
				.setActions(deviceActionAvros(hubEvent))
				.setConditions(getScenarioConditionAvros(hubEvent))
				.build();
	}

	private List<DeviceActionAvro> deviceActionAvros(ScenarioAddedEvent event) {
		return event.getActions().stream()
				.map(action -> DeviceActionAvro.newBuilder()
						.setSensorId(action.getSensorId())
						.setType(ActionTypeAvro.valueOf(action.getType().name()))
						.setValue(action.getValue())
						.build())
				.toList();
	}

	private List<ScenarioConditionAvro> getScenarioConditionAvros(ScenarioAddedEvent event) {
		return event.getConditions().stream()
				.map(condition -> ScenarioConditionAvro.newBuilder()
						.setSensorId(condition.getSensorId())
						.setType(ConditionTypeAvro.valueOf(condition.getType().name()))
						.setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
						.setValue(condition.getValue())
						.build())
				.toList();
	}
}
