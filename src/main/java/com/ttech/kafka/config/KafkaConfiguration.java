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
	
	
	
	@Value("${spring.kafka.ssl.key-store-location}")
	private String sslKeyStoreLocation;

	@Value("${spring.kafka.ssl.key-store-password}")
	private String sslKeyStorePassword;
	
	
	
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,sslTrustStoreLocation);
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, sslTrustStorePassword);
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, sslKeyStoreLocation);
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, sslKeyStorePassword);
		
		props.put(ProducerConfig.RETRIES_CONFIG, 3);
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SSL");
		props.put(ProducerConfig.ACKS_CONFIG,"1");
		props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
		 

		return new DefaultKafkaProducerFactory<>(props);
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
