package com.ram.food.ordering.system.kafka.producer.config;

import com.ram.food.ordering.system.kafka.config.data.KafkaConfigProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConfigProperties kafkaConfigProperties;
  private final KafkaConfigProperties kafkaProducerConfigProperties;

  @Bean
  public Map<String, Object> producerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put("Boot1", kafkaConfigProperties.bootstrapServers());
    props.put("Boot2", kafkaProducerConfigProperties.bootstrapServers());
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
