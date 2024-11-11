package ru.yandex.practicum.controller;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ru.yandex.practicum.service.EventService;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {
	private final EventService eventService;

	@PostMapping("/sensors")
	public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
		eventService.addSensorEvent(event);
	}

	@PostMapping("/hubs")
	public void collectHubEvent(@Valid @RequestBody HubEvent hubEvent) {
		eventService.addHubEvent(hubEvent);
	}
}