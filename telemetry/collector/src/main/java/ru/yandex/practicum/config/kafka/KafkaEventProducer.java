package ru.yandex.practicum.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventProducer {
	private final KafkaProducer<String, SpecificRecordBase> kafkaProducer;

	public <T extends SpecificRecordBase> void send(String topic, T record) {
		ProducerRecord<String, SpecificRecordBase> producerRecord = new ProducerRecord<>(topic, record);

		kafkaProducer.send(producerRecord);
	}
}
