package ru.yandex.practicum.handler.hub.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.handler.hub.HubHandlerEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ScenarioRepository;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedHandler implements HubHandlerEvent {
	private final ScenarioRepository repository;

	@Override
	public String getType() {
		return ScenarioRemovedEventAvro.class.getName();
	}

	@Override
	public void handle(HubEventAvro event) {
		ScenarioRemovedEventAvro removedEvent = (ScenarioRemovedEventAvro) event.getPayload();
		repository.findByHubIdAndName(event.getHubId(), removedEvent.getName())
				.ifPresent(repository::delete);
	}
}
