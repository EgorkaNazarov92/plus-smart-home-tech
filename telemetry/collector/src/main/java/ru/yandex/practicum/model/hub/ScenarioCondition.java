package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScenarioCondition {
	private String sensorId;
	private ScenarioConditionType type;
	private ScenarioConditionOperation operation;
	Object value;
}
