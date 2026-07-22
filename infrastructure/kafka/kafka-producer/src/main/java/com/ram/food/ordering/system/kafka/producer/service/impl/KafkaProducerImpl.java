package com.ram.food.ordering.system.kafka.producer.service.impl;

import com.ram.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import com.ram.food.ordering.system.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase>
    implements KafkaProducer<K, V> {

  private final KafkaTemplate<K, V> kafkaTemplate;

  @Override
  public CompletableFuture<SendResult<K, V>> send(String topicName, K key, V value) {
    log.info("Sending message={} to topic={}", value, topicName);
    try {
      return kafkaTemplate.send(topicName, key, value);
    } catch (KafkaException e) {
      log.error("Error on Kafka producer with key: {}, message: {}", key, value, e);
      throw new KafkaProducerException(
          "Error on Kafka producer with key: " + key + " and message: " + value);
    }
  }

  @PreDestroy
  public void close() {
    if (kafkaTemplate != null) {
      log.info("Closing Kafka producer");
      kafkaTemplate.destroy();
    }
  }
}
