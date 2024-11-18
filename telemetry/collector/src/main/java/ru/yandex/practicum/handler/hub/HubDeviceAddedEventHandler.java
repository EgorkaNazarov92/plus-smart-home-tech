package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;

@HandlerHubEvent(HubEventType.DEVICE_ADDED)
@Component
public class HubDeviceAddedEventHandler extends HubEventHandler<DeviceAddedEventAvro> {

	public HubDeviceAddedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected DeviceAddedEventAvro mapToAvro(HubEvent event) {
		DeviceAddedEvent hubEvent = (DeviceAddedEvent) event;
		return DeviceAddedEventAvro.newBuilder()
				.setId(hubEvent.getId())
				.setType(DeviceTypeAvro.valueOf(hubEvent.getDeviceType().name()))
				.build();
	}
}
