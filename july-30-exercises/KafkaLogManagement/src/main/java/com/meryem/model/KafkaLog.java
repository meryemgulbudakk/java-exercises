package com.meryem.model;

import java.sql.Timestamp;

/**
 * This class represents a Kafka log record using Java's record type.
 * Each log entry has an ID, timestamp, topic name, partition number,
 * offset, key, and value.
 */
public record KafkaLog(Integer id, Timestamp timestamp, String topic, int partition, int offset, String key, String value) {
    // Record automatically generates the constructor, getters, equals, hashCode, and toString methods.
}
