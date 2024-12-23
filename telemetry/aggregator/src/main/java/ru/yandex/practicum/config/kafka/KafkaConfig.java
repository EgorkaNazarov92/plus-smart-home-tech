package ru.yandex.practicum.config.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.serializer.BaseAvroSerializer;
import ru.yandex.practicum.serializer.SensorEventDeserializer;

import java.util.Properties;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaConfig {
	@Value("${aggregator.kafka.bootstrapServer}")
	String bootstrapServer;
	@Value("${aggregator.kafka.group.id}")
	String groupId;
	@Getter
	@Value("${aggregator.kafka.topic.in}")
	String topicIn;
	@Getter
	@Value("${aggregator.kafka.topic.out}")
	String topicOut;

	@Bean
	public KafkaProducer<String, SpecificRecordBase> getProducer() {
		Properties configProps = new Properties();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BaseAvroSerializer.class);

		return new KafkaProducer<>(configProps);
	}

	@Bean
	public KafkaConsumer<String, SensorEventAvro> getConsumer() {
		Properties configProps = new Properties();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorEventDeserializer.class);
		return new KafkaConsumer<>(configProps);
	}
}
