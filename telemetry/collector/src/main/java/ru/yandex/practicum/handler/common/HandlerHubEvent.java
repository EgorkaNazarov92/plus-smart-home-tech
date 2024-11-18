package ru.yandex.practicum.handler.common;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerHubEvent {
	HubEventProto.PayloadCase value();
}

