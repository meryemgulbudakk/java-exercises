package com.meryem.model.database;

import com.meryem.model.KafkaLog;
import com.meryem.model.dao.KafkaLogDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseConnection class implements KafkaLogDAO interface to interact with the MySQL database
 * for Kafka log-related CRUD operations. It uses JDBC to manage the connection and execute
 * SQL queries for creating, reading, updating, and deleting Kafka log entries.
 */
public class DatabaseConnection implements KafkaLogDAO {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/kafka_logs_db";
    private static final String USER = "meryem";
    private static final String PASSWORD = "123456789";

    // Static block to load the MySQL JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found.", e);
            throw new RuntimeException("JDBC Driver not found.", e);
        }
    }

    /**
     * Establishes a connection to the database using the specified URL, user, and password.
     *
     * @return Connection object representing the database connection
     * @throws SQLException if there is an error establishing the connection
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Inserts a new KafkaLog entry into the database.
     *
     * @param kafkaLog The KafkaLog object to be inserted.
     * @throws SQLException if an error occurs during the database operation
     */
    @Override
    public void create(KafkaLog kafkaLog) throws SQLException {
        String sql = "INSERT INTO logs (timestamp, topic, `partition`, `offset`, `key`, `value`) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting the prepared statement parameters
            preparedStatement.setTimestamp(1, kafkaLog.timestamp());
            preparedStatement.setString(2, kafkaLog.topic());
            preparedStatement.setInt(3, kafkaLog.partition());
            preparedStatement.setInt(4, kafkaLog.offset());
            preparedStatement.setString(5, kafkaLog.key());
            preparedStatement.setString(6, kafkaLog.value());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating KafkaLog failed, no rows affected.");
            }

            // Retrieve the generated ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    logger.info("Inserted KafkaLog with generated ID: {}", generatedId);
                } else {
                    throw new SQLException("Creating KafkaLog failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting KafkaLog: {}", kafkaLog, e);
            throw e;
        }
    }

    /**
     * Retrieves all KafkaLog entries from the database.
     *
     * @return A list of KafkaLog objects representing all logs in the database
     */
    @Override
    public List<KafkaLog> getAll() {
        String sql = "SELECT * FROM logs";
        List<KafkaLog> kafkaLogs = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Iterating over the result set to create KafkaLog objects
            while (resultSet.next()) {
                KafkaLog kafkaLog = new KafkaLog(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("timestamp"),
                        resultSet.getString("topic"),
                        resultSet.getInt("partition"),
                        resultSet.getInt("offset"),
                        resultSet.getString("key"),
                        resultSet.getString("value")
                );
                kafkaLogs.add(kafkaLog);
            }
            logger.info("Retrieved all KafkaLogs");
        } catch (SQLException e) {
            logger.error("Error retrieving all KafkaLogs", e);
        }
        return kafkaLogs;
    }

    /**
     * Retrieves a KafkaLog entry by its ID.
     *
     * @param id The ID of the KafkaLog to be retrieved
     * @return The KafkaLog object with the specified ID, or null if not found
     */
    @Override
    public KafkaLog getById(int id) {
        String sql = "SELECT * FROM logs WHERE id = ?";
        KafkaLog kafkaLog = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result is found and create a KafkaLog object
            if (resultSet.next()) {
                kafkaLog = new KafkaLog(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("timestamp"),
                        resultSet.getString("topic"),
                        resultSet.getInt("partition"),
                        resultSet.getInt("offset"),
                        resultSet.getString("key"),
                        resultSet.getString("value")
                );
                logger.info("Retrieved KafkaLog with id: {}", id);
            } else {
                logger.warn("No KafkaLog found with id: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving KafkaLog with id: {}", id, e);
        }
        return kafkaLog;
    }

    /**
     * Updates an existing KafkaLog entry in the database.
     *
     * @param kafkaLog The KafkaLog object containing updated information
     */
    @Override
    public void update(KafkaLog kafkaLog) {
        String sql = "UPDATE logs SET timestamp = ?, topic = ?, `partition` = ?, `offset` = ?, `key` = ?, `value` = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Setting the prepared statement parameters
            preparedStatement.setTimestamp(1, kafkaLog.timestamp());
            preparedStatement.setString(2, kafkaLog.topic());
            preparedStatement.setInt(3, kafkaLog.partition());
            preparedStatement.setInt(4, kafkaLog.offset());
            preparedStatement.setString(5, kafkaLog.key());
            preparedStatement.setString(6, kafkaLog.value());
            preparedStatement.setInt(7, kafkaLog.id());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating KafkaLog failed, no rows affected.");
            }

            logger.info("Updated KafkaLog with id: {}", kafkaLog.id());
        } catch (SQLException e) {
            logger.error("Error updating KafkaLog with id: {}", kafkaLog.id(), e);
        }
    }

    /**
     * Deletes a KafkaLog entry from the database.
     *
     * @param kafkaLog The KafkaLog object to be deleted
     */
    @Override
    public void delete(KafkaLog kafkaLog) {
        String sql = "DELETE FROM logs WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, kafkaLog.id());
            preparedStatement.executeUpdate();
            logger.info("Deleted KafkaLog with id: {}", kafkaLog.id());
        } catch (SQLException e) {
            logger.error("Error deleting KafkaLog with id: {}", kafkaLog.id(), e);
        }
    }
}
