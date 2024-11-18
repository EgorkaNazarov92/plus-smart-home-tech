package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.common.HandlerHubEvent;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;

@HandlerHubEvent(HubEventProto.PayloadCase.SCENARIO_ADDED)
@Component
public class HubScenarioAddedEventHandler extends HubEventHandler<ScenarioAddedEventAvro> {

	public HubScenarioAddedEventHandler(KafkaConfig config, KafkaEventProducer producer) {
		super(config, producer);
	}

	@Override
	protected ScenarioAddedEventAvro mapToAvro(HubEventProto event) {
		ScenarioAddedEventProto hubEvent = event.getScenarioAdded();
		return ScenarioAddedEventAvro.newBuilder()
				.setName(hubEvent.getName())
				.setActions(deviceActionAvros(hubEvent))
				.setConditions(getScenarioConditionAvros(hubEvent))
				.build();
	}

	private List<DeviceActionAvro> deviceActionAvros(ScenarioAddedEventProto event) {
		return event.getActionList().stream()
				.map(action -> DeviceActionAvro.newBuilder()
						.setSensorId(action.getSensorId())
						.setType(ActionTypeAvro.valueOf(action.getType().name()))
						.setValue(action.getValue())
						.build())
				.toList();
	}

	private List<ScenarioConditionAvro> getScenarioConditionAvros(ScenarioAddedEventProto event) {
		return event.getConditionList().stream()
				.map(condition -> ScenarioConditionAvro.newBuilder()
						.setSensorId(condition.getSensorId())
						.setType(ConditionTypeAvro.valueOf(condition.getType().name()))
						.setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
						.setValue(condition.hasBoolValue() ? condition.getBoolValue() : condition.getIntValue())
						.build())
				.toList();
	}
}
