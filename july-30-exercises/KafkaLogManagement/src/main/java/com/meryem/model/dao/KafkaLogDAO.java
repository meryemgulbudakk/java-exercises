package com.meryem.model.dao;

import com.meryem.model.KafkaLog;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface defines the Data Access Object (DAO) for Kafka logs.
 * It provides CRUD (Create, Read, Update, Delete) operations on the KafkaLog data.
 * Implementations of this interface will interact with the database to perform these operations.
 */
public interface KafkaLogDAO {

    /**
     * Inserts a new Kafka log into the database.
     *
     * @param kafkaLog The KafkaLog object to be inserted.
     * @throws SQLException if there is an error during the database operation.
     */
    void create(KafkaLog kafkaLog) throws SQLException;

    /**
     * Updates an existing Kafka log in the database.
     *
     * @param kafkaLog The KafkaLog object with updated information.
     * @throws SQLException if there is an error during the database operation.
     */
    void update(KafkaLog kafkaLog) throws SQLException;

    /**
     * Deletes a Kafka log from the database.
     *
     * @param kafkaLog The KafkaLog object to be deleted.
     * @throws SQLException if there is an error during the database operation.
     */
    void delete(KafkaLog kafkaLog) throws SQLException;

    /**
     * Retrieves a Kafka log by its ID from the database.
     *
     * @param id The ID of the Kafka log to be retrieved.
     * @return The KafkaLog object with the specified ID, or null if not found.
     * @throws SQLException if there is an error during the database operation.
     */
    KafkaLog getById(int id) throws SQLException;

    /**
     * Retrieves all Kafka logs from the database.
     *
     * @return A list of all KafkaLog objects.
     * @throws SQLException if there is an error during the database operation.
     */
    List<KafkaLog> getAll() throws SQLException;
}
