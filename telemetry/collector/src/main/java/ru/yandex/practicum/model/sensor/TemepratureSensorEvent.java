package ru.yandex.practicum.model.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TemepratureSensorEvent extends SensorEvent {
	private Integer temperatureC;
	private Integer temperatureF;

	@Override
	public SensorEventType getType() {
		return SensorEventType.TEMPERATURE_SENSOR_EVENT;
	}
}