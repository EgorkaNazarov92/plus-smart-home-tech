package ru.yandex.practicum.handler.hub.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.handler.hub.HubHandlerEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.model.types.ActionType;
import ru.yandex.practicum.model.types.ConditionOperation;
import ru.yandex.practicum.model.types.ConditionType;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.repository.SensorRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScenarioAddedHandler implements HubHandlerEvent {
	private final ScenarioRepository scenarioRepository;
	private final SensorRepository sensorRepository;

	@Override
	public String getType() {
		return ScenarioAddedEventAvro.class.getName();
	}

	@Override
	public void handle(HubEventAvro event) {
		ScenarioAddedEventAvro addedEvent = (ScenarioAddedEventAvro) event.getPayload();
		if (scenarioRepository.findByHubIdAndName(event.getHubId(), addedEvent.getName()).isEmpty()) {
			Scenario scenario = Scenario.builder()
					.hubId(event.getHubId())
					.name(addedEvent.getName())
					.conditions(getConditions(addedEvent.getConditions()))
					.actions(getActions(addedEvent.getActions()))
					.build();
			scenario.getConditions().forEach(condition -> condition.setScenario(scenario));
			scenario.getActions().forEach(action -> action.setScenario(scenario));
			scenarioRepository.save(scenario);
		}
	}

	private List<Condition> getConditions(List<ScenarioConditionAvro> avroConditions) {
		return avroConditions.stream()
				.map(condition -> Condition.builder()
						.type(ConditionType.valueOf(condition.getType().name()))
						.sensor(sensorRepository.findById(condition.getSensorId()).orElseThrow())
						.operation(ConditionOperation.valueOf(condition.getOperation().name()))
						.value(getIntValue(condition.getValue()))
						.build())
				.toList();
	}

	private List<Action> getActions(List<DeviceActionAvro> avroActions) {
		return avroActions.stream()
				.map(action -> Action.builder()
						.sensor(sensorRepository.findById(action.getSensorId()).orElse(null))
						.type(ActionType.valueOf(action.getType().name()))
						.value(action.getValue() == null ? 0 : action.getValue())
						.build())
				.toList();
	}

	private Integer getIntValue(Object value) {
		if (value instanceof Integer) return (Integer) value;
		else if (value instanceof Boolean) return (Boolean) value ? 1 : 0;
		else return null;
	}
}