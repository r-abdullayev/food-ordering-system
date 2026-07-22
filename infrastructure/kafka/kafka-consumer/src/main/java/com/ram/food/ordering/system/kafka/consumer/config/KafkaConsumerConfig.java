package com.ram.food.ordering.system.kafka.consumer.config;

import com.ram.food.ordering.system.kafka.config.data.KafkaConfigProperties;
import com.ram.food.ordering.system.kafka.config.data.KafkaConsumerConfigProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConfigProperties kafkaConfigProperties;
  private final KafkaConsumerConfigProperties kafkaConsumerConfigProperties;

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.bootstrapServers());
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        kafkaConsumerConfigProperties.keyDeserializer());
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        kafkaConsumerConfigProperties.valueDeserializer());
    props.put(
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerConfigProperties.autoOffsetReset());
    props.put(
        kafkaConfigProperties.schemaRegistryUrlKey(), kafkaConfigProperties.schemaRegistryUrl());
    props.put(
        kafkaConsumerConfigProperties.specificAvroReaderKey(),
        kafkaConsumerConfigProperties.specificAvroReader());
    props.put(
        ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerConfigProperties.sessionTimeoutMs());
    props.put(
        ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,
        kafkaConsumerConfigProperties.heartbeatIntervalMs());
    props.put(
        ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
        kafkaConsumerConfigProperties.maxPollIntervalMs());
    props.put(
        ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
        kafkaConsumerConfigProperties.maxPartitionFetchBytesDefault()
            * kafkaConsumerConfigProperties.maxPartitionFetchBytesBoostFactor());
    props.put(
        ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaConsumerConfigProperties.maxPollRecords());
    return props;
  }

  @Bean
  public ConsumerFactory<K, V> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>>
      kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<K, V> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setBatchListener(kafkaConsumerConfigProperties.batchListener());
    factory.setConcurrency(kafkaConsumerConfigProperties.concurrencyLevel());
    factory.setAutoStartup(kafkaConsumerConfigProperties.autoStartup());
    factory.getContainerProperties().setPollTimeout(kafkaConsumerConfigProperties.pollTimeoutMs());
    return factory;
  }
}
