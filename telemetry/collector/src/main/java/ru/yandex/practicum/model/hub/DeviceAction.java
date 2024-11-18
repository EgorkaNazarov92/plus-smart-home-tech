package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceAction {
	private String sensorId;
	private ActionType type;
	Integer value;
}
