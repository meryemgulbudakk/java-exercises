package com.meryem.controller.servlets;

import com.meryem.utils.serialization.*;
import com.meryem.model.KafkaLog;
import com.meryem.model.dao.KafkaLogDAO;
import com.meryem.model.database.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CrudServlet class handles HTTP requests for CRUD operations on Kafka logs.
 * It interacts with the KafkaLogDAO to perform database operations and
 * uses Gson for JSON parsing and serialization.
 */
@WebServlet("/api/*")
public class CrudServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CrudServlet.class);
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Timestamp.class, new TimestampJsonSerializer())
            .registerTypeAdapter(Timestamp.class, new TimestampJsonDeserializer())
            .create();
    private KafkaLogDAO kafkaLogDAO = new DatabaseConnection();

    /**
     * Handles POST requests to create a new Kafka log entry.
     *
     * @param request  The HttpServletRequest object containing the request data.
     * @param response The HttpServletResponse object used to send a response to the client.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException      If an error occurs while reading or writing data.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if ("/logs".equals(pathInfo)) {
            KafkaLog newLog;
            try {
                // Parse JSON from the request body into a KafkaLog object
                newLog = gson.fromJson(request.getReader(), KafkaLog.class);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendJsonResponse(response, "Invalid JSON data");
                logger.warn("Invalid JSON input: {}", e.getMessage());
                return;
            }

            // Validate the required fields in the KafkaLog object
            if (newLog.topic() == null || newLog.topic().isEmpty() ||
                    newLog.partition() < 0 || newLog.offset() < 0 ||
                    newLog.timestamp() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendJsonResponse(response, "Invalid input data");
                logger.warn("Invalid input data: {}", newLog);
                return;
            }

            try {
                // Call DAO to create a new Kafka log entry in the database
                kafkaLogDAO.create(newLog);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_CREATED);
                sendJsonResponse(response, newLog);
                logger.info("Kafka log created: {}", newLog);
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                sendJsonResponse(response, "Error creating Kafka log: " + e.getMessage());
                logger.error("Error creating Kafka log: {}", e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendJsonResponse(response, "Invalid endpoint");
        }
    }

    /**
     * Handles GET requests to retrieve Kafka log entries by ID or list all entries.
     *
     * @param request  The HttpServletRequest object containing the request data.
     * @param response The HttpServletResponse object used to send a response to the client.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException      If an error occurs while reading or writing data.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // Retrieve Kafka log entry by ID
        if (pathInfo != null && pathInfo.matches("/logs/\\d+")) {
            int id = parseIdFromPath(pathInfo);

            try {
                KafkaLog kafkaLog = kafkaLogDAO.getById(id);
                if (kafkaLog == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    sendJsonResponse(response, "Kafka log not found");
                    logger.warn("Kafka log not found with id: {}", id);
                    return;
                }

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                sendJsonResponse(response, kafkaLog);
                logger.info("Successfully retrieved Kafka log with id: {}", id);
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                sendJsonResponse(response, "Error retrieving Kafka log");
                logger.error("Error retrieving Kafka log: {}", e.getMessage());
            }
        }
        // Retrieve all Kafka log entries
        else if ("/logs".equals(pathInfo)) {
            try {
                List<KafkaLog> kafkaLogs = kafkaLogDAO.getAll();
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                sendJsonResponse(response, kafkaLogs);
                logger.info("Successfully retrieved and returned Kafka logs");
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                sendJsonResponse(response, "Error retrieving Kafka logs");
                logger.error("Error retrieving Kafka logs: {}", e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendJsonResponse(response, "Invalid endpoint");
        }
    }

    /**
     * Handles PUT requests to update an existing Kafka log entry by ID.
     *
     * @param request  The HttpServletRequest object containing the request data.
     * @param response The HttpServletResponse object used to send a response to the client.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException      If an error occurs while reading or writing data.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.matches("/logs/\\d+")) {
            int id = parseIdFromPath(pathInfo);

            KafkaLog updatedKafkaLog;
            try {
                // Parse JSON from the request body into a KafkaLog object
                updatedKafkaLog = gson.fromJson(request.getReader(), KafkaLog.class);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendJsonResponse(response, "Invalid JSON data");
                logger.warn("Invalid JSON input: {}", e.getMessage());
                return;
            }

            // Validate the updated KafkaLog object
            if (updatedKafkaLog == null || updatedKafkaLog.topic() == null || updatedKafkaLog.topic().isEmpty()
                    || updatedKafkaLog.partition() < 0 || updatedKafkaLog.offset() < 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendJsonResponse(response, "Invalid input data");
                logger.warn("Invalid input data for updating Kafka log");
                return;
            }

            try {
                // Retrieve the existing Kafka log entry from the database
                KafkaLog existingKafkaLog = kafkaLogDAO.getById(id);
                if (existingKafkaLog == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    sendJsonResponse(response, "Kafka log not found");
                    logger.warn("Kafka log not found with id: {}", id);
                    return;
                }

                // Create a new KafkaLog object to update the existing log
                KafkaLog kafkaLogToUpdate = new KafkaLog(id, updatedKafkaLog.timestamp(), updatedKafkaLog.topic(),
                        updatedKafkaLog.partition(), updatedKafkaLog.offset(), updatedKafkaLog.key(), updatedKafkaLog.value());

                // Call DAO to update the Kafka log entry in the database
                kafkaLogDAO.update(kafkaLogToUpdate);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                sendJsonResponse(response, kafkaLogToUpdate);
                logger.info("Successfully updated Kafka log with id: {}", id);
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                sendJsonResponse(response, "Error updating Kafka log");
                logger.error("Error updating Kafka log: {}", e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendJsonResponse(response, "Invalid endpoint");
        }
    }

    /**
     * Handles DELETE requests to remove a Kafka log entry by ID.
     *
     * @param request  The HttpServletRequest object containing the request data.
     * @param response The HttpServletResponse object used to send a response to the client.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException      If an error occurs while reading or writing data.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.matches("/logs/\\d+")) {
            int id = parseIdFromPath(pathInfo);

            try {
                // Retrieve the Kafka log entry to be deleted
                KafkaLog kafkaLog = kafkaLogDAO.getById(id);
                if (kafkaLog == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    sendJsonResponse(response, "Kafka log not found");
                    logger.warn("Kafka log not found with id: {}", id);
                    return;
                }

                // Call DAO to delete the Kafka log entry from the database
                kafkaLogDAO.delete(kafkaLog);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Successfully deleted Kafka log with id: {}", id);
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                sendJsonResponse(response, "Error deleting Kafka log");
                logger.error("Error deleting Kafka log: {}", e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendJsonResponse(response, "Invalid endpoint");
        }
    }

    /**
     * Parses the ID from the URL path.
     *
     * @param pathInfo The path information from the request URL.
     * @return The ID extracted from the path.
     */
    private int parseIdFromPath(String pathInfo) {
        try {
            return Integer.parseInt(pathInfo.substring(pathInfo.lastIndexOf('/') + 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format in path");
        }
    }

    /**
     * Sends a JSON response to the client.
     *
     * @param response The HttpServletResponse object used to send a response.
     * @param object   The object to be converted to JSON and sent in the response.
     * @throws IOException If an error occurs while writing data to the response.
     */
    private void sendJsonResponse(HttpServletResponse response, Object object) throws IOException {
        try (var out = response.getWriter()) {
            out.print(gson.toJson(object));
            out.flush();
        }
    }
}
