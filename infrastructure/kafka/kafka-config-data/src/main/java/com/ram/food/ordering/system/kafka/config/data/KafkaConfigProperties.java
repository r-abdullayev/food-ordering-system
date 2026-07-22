package com.ram.food.ordering.system.kafka.config.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka-config")
public record KafkaConfigProperties(
    String bootstrapServers,
    String schemaRegistryUrlKey,
    String schemaRegistryUrl,
    Integer numOfPartitions,
    Short replicationFactor) {}
