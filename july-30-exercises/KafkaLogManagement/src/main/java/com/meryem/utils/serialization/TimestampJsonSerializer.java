package com.meryem.utils.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Custom serializer for java.sql.Timestamp objects to JSON format.
 * This class uses Gson's JsonSerializer interface to define how a Timestamp
 * should be serialized (converted to a string) when sending JSON responses.
 */
public class TimestampJsonSerializer implements JsonSerializer<Timestamp> {

    // Define a date format to convert the Timestamp to a readable string (yyyy-MM-dd HH:mm:ss).
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * This method is used to convert a Timestamp object to a JSON string.
     *
     * @param timestamp The Timestamp object to be serialized.
     * @param typeOfSrc The actual type of the source object.
     * @param context   The context of serialization.
     * @return A JsonElement representing the formatted Timestamp string.
     */
    @Override
    public JsonElement serialize(Timestamp timestamp, Type typeOfSrc, JsonSerializationContext context) {
        // Serialize the Timestamp into the defined date format and return it as a JSON element.
        return context.serialize(dateFormat.format(timestamp));
    }
}
