package com.ram.food.ordering.system.kafka.consumer;

import java.util.List;
import org.apache.avro.specific.SpecificRecordBase;

public class KafkaConsumerImpl implements KafkaConsumer<SpecificRecordBase> {
  @Override
  public void consume(
      List<SpecificRecordBase> messages,
      List<Long> keys,
      List<Long> partitions,
      List<Long> offsets) {}
}
