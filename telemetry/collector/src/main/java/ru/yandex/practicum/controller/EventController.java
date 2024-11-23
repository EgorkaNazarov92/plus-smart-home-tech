package ru.yandex.practicum.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.handler.HubEventHandler;
import ru.yandex.practicum.handler.SensorEventHandler;
import ru.yandex.practicum.handler.common.CommonHandler;

@GrpcService
@RequiredArgsConstructor
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {
	private final CommonHandler commonHandler;

	@Override
	public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
		try {
			SensorEventHandler<? extends SpecificRecordBase> handler = commonHandler
					.getSensorEventHandlers().get(request.getPayloadCase());
			if (handler == null)
				throw new NotFoundException(request.getPayloadCase().name() + " not found");

			handler.handle(request);

			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(new StatusRuntimeException(
					Status.INTERNAL
							.withDescription(e.getLocalizedMessage())
							.withCause(e)
			));
		}
	}

	@Override
	public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
		try {
			HubEventHandler<? extends SpecificRecordBase> handler = commonHandler
					.getHubEventHandlers().get(request.getPayloadCase());
			if (handler == null)
				throw new NotFoundException(request.getPayloadCase().name() + " not found");

			handler.handle(request);

			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(new StatusRuntimeException(
					Status.INTERNAL
							.withDescription(e.getLocalizedMessage())
							.withCause(e)
			));
		}
	}
}
