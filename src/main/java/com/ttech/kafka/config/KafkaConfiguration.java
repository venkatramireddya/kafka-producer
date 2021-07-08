package com.ttech.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfiguration {

	/**
	 * The bootstrap server list for kafka authentication
	 */
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServer;
	
	/**
	 * The location of trust/key store for kafka authentication
	 */
	@Value("${spring.kafka.ssl.trust-store-location}")
	private String sslTrustStoreLocation;
	
	/**
	 * The Password of trust/key store for kafka authentication
	 */
	@Value("${spring.kafka.ssl.trust-store-password}")
	private String sslTrustStorePassword;
	
	/**
	 * The Type of trust/key store for kafka authentication
	 */
	@Value("${spring.kafka.ssl.trust-store-type}")
	private String sslTrustStoreType;
	
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		 props.put(ProducerConfig.RETRIES_CONFIG, 3);
		/*
		 *  props.put(ProducerConfig.RETRIES_CONFIG, 3);
		 * props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"");
		 * props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "");
		 * props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "");
		 * props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "");
		 * props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "");
		 * props.put(SslConfigs.SSL_PROTOCOL_CONFIG, "TLS");
		 * props.put(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, "TLS");
		 * props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
		 */

		return new DefaultKafkaProducerFactory<>(props);
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
