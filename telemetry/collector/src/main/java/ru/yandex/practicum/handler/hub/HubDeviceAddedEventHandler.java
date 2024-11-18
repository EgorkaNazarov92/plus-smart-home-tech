package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

@HandlerHubEvent(HubEventProto.PayloadCase.DEVICE_ADDED)
@Component
public class HubDeviceAddedEventHandler extends HubEventHandler<DeviceAddedEventAvro> {

	public HubDeviceAddedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected DeviceAddedEventAvro mapToAvro(HubEventProto event) {
		DeviceAddedEventProto hubEvent = event.getDeviceAdded();
		return DeviceAddedEventAvro.newBuilder()
				.setId(hubEvent.getId())
				.setType(DeviceTypeAvro.valueOf(hubEvent.getType().name()))
				.build();
	}
}
