package com.ram.food.ordering.system.kafka.consumer;

import java.util.List;
import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaConsumer<T extends SpecificRecordBase> {
  void consume(List<T> messages, List<Long> keys, List<Long> partitions, List<Long> offsets);
}
