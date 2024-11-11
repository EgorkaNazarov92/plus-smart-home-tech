package ru.yandex.practicum.handler.common;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.model.sensor.SensorEventType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommonHandler {
	private final BeanSorter beanSorter;

	@Getter
	private Map<Enum<?>, SensorEventHandler<? extends SpecificRecordBase>> sensorEventHandlers = new HashMap<>();

	@PostConstruct
	public void init() {
		sensorEventHandlers = beanSorter.getAnnotatedBeans(HandlerSensorEvent.class).stream()
				.map(h -> (SensorEventHandler<?>) h)
				.collect(Collectors.toMap(CommonHandler::getValueSensor, h -> h));
	}

	private static SensorEventType getValueSensor(SensorEventHandler<?> handler) {
		HandlerSensorEvent handlerAnnotation = handler.getClass().getAnnotation(HandlerSensorEvent.class);
		if (handlerAnnotation == null) {
			throw new IllegalArgumentException("No annotation found for " + handler.getClass().getName());
		}
		return handlerAnnotation.value();
	}

}
