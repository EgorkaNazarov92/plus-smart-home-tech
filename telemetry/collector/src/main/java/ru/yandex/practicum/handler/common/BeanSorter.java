package ru.yandex.practicum.handler.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class BeanSorter {
	private final ApplicationContext context;

	public Collection<Object> getAnnotatedBeans(final Class<? extends Annotation> clazz) {
		return context.getBeansWithAnnotation(clazz).values();
	}
}
