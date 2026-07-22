package com.ram.food.ordering.system.kafka.producer.config;

import com.ram.food.ordering.system.kafka.config.data.KafkaConfigProperties;
import com.ram.food.ordering.system.kafka.config.data.KafkaProducerConfigProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConfigProperties kafkaConfigProperties;
  private final KafkaProducerConfigProperties kafkaProducerConfigProperties;

  @Bean
  public Map<String, Object> producerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.bootstrapServers());
    props.put(
        kafkaConfigProperties.schemaRegistryUrlKey(), kafkaConfigProperties.schemaRegistryUrl());
    props.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        kafkaProducerConfigProperties.keySerializerClass());
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        kafkaProducerConfigProperties.valueSerializerClass());
    props.put(
        ProducerConfig.BATCH_SIZE_CONFIG,
        kafkaProducerConfigProperties.batchSize()
            * kafkaProducerConfigProperties.batchSizeBoostFactor());
    props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigProperties.lingerMs());
    props.put(
        ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigProperties.compressionType());
    props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigProperties.acks());
    props.put(
        ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigProperties.requestTimeoutMs());
    props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigProperties.retryCount());
    return props;
  }

  @Bean
  public ProducerFactory<K, V> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfig());
  }

  @Bean
  public KafkaTemplate<K, V> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
