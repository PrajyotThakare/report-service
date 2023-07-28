package com.prajyot.report.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.prajyot.report.model.JsonObject;

//This is config class for kafka consumer
@Configuration
@EnableKafka
public class KafkaConfig {

	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-group-id-1");
		return new DefaultKafkaConsumerFactory<>(config);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	} 
	
	@Bean
	public ConsumerFactory<String, JsonObject> userConsumerFactory(){

		JsonDeserializer<JsonObject> deserializer = new JsonDeserializer<>(JsonObject.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-group-id-2");
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, JsonObject> userKafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, JsonObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	} 
}
