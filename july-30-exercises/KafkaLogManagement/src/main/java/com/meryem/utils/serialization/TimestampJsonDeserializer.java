package com.meryem.utils.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.reflect.Type;

/**
 * Custom deserializer for converting JSON strings into java.sql.Timestamp objects.
 * This class implements Gson's JsonDeserializer interface to define how a JSON string
 * should be converted into a Timestamp object when receiving JSON requests.
 */
public class TimestampJsonDeserializer implements JsonDeserializer<Timestamp> {

    // Define the date format to parse the Timestamp (yyyy-MM-dd HH:mm:ss).
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * This method is used to convert a JSON string into a Timestamp object.
     *
     * @param json    The JSON element containing the date string.
     * @param typeOfT The type of the object to deserialize to.
     * @param context The context of deserialization.
     * @return A Timestamp object parsed from the JSON string.
     * @throws JsonParseException If the date string cannot be parsed.
     */
    @Override
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            // Parse the JSON string into a date and return a Timestamp object.
            return new Timestamp(dateFormat.parse(json.getAsString()).getTime());
        } catch (ParseException e) {
            // Throw a JsonParseException if the date cannot be parsed.
            throw new JsonParseException(e);
        }
    }
}
