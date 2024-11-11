package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;

@HandlerHubEvent(HubEventType.DEVICE_REMOVED)
@Component
public class HubDeviceRemovedEventHandler extends HubEventHandler<DeviceRemovedEventAvro> {

	public HubDeviceRemovedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected DeviceRemovedEventAvro mapToAvro(HubEvent event) {
		DeviceRemovedEvent hubEvent = (DeviceRemovedEvent) event;
		return DeviceRemovedEventAvro.newBuilder()
				.setId(hubEvent.getId())
				.build();
	}
}
