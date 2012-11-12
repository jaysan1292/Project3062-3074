package com.jaysan1292.project.common.data;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class JSONSerializable<E> {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    /**
     * Reads a JSON-encoded array into an {@code ArrayList} containing its elements.
     *
     * @param cls  The class of the objects to deserialize.
     * @param json The JSON-encoded string.
     *
     * @return An {@code ArrayList} containing the objects that were encoded in the JSON array.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> ArrayList<T> readJSONArray(Class<T> cls, String json) throws IOException {
        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, cls));
    }

    /**
     * Reads a JSON-encoded array into an {@code ArrayList} containing its elements.
     *
     * @param cls    The class of the objects to deserialize.
     * @param reader A {@code Reader} pointing to an input stream that contains a JSON-encoded object array.
     *
     * @return An {@code ArrayList} containing the objects that were encoded in the JSON array.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> ArrayList<T> readJSONArray(Class<T> cls, Reader reader) throws IOException {
        return mapper.readValue(reader, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, cls));
    }

    /**
     * Reads a JSON_encoded array into an {@code ArrayList} containing its elements.
     *
     * @param cls    The class of the objects to deserialize.
     * @param stream An {@code InputStream} pointing to a stream that contains a JSON-encoded object array.
     *
     * @return An {@code ArrayList} containing the objects that were encoded in the JSON array.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> ArrayList<T> readJSONArray(Class<T> cls, InputStream stream) throws IOException {
        return mapper.readValue(stream, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, cls));
    }

    /**
     * Reads a JSON-encoded array into a {@code Set} containing its elements.
     *
     * @param cls  The class of the objects to deserialize.
     * @param json The JSON-encoded string.
     *
     * @return A {@code Set} containing the objects that were encoded in the JSON array.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> Set<T> readJSONSet(Class<T> cls, String json) throws IOException {
        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(Set.class, cls));
    }

    /**
     * Reads a JSON-encoded array into a {@code Set} containing its elements.
     *
     * @param cls    The class of the objects to deserialize.
     * @param reader A {@code Reader} pointing to an input stream that contains a JSON-encoded object array.
     *
     * @return A {@code Set} containing the objects that were encoded in the JSON array.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> Set<T> readJSONSet(Class<T> cls, Reader reader) throws IOException {
        return mapper.readValue(reader, TypeFactory.defaultInstance().constructCollectionType(Set.class, cls));
    }

    /**
     * Reads a JSON-encoded array into a {@code Set} containing its elements.
     *
     * @param cls    The class of the objects to deserialize.
     * @param stream An {@code InputStream} pointing to a stream that contains a JSON-encoded object array.
     *
     * @return A {@code Set} containing the objects that were encoded in the JSON array.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> Set<T> readJSONSet(Class<T> cls, InputStream stream) throws IOException {
        return mapper.readValue(stream, TypeFactory.defaultInstance().constructCollectionType(Set.class, cls));
    }

    /**
     * Serializes a {@code List} containing objects that extend {@code JSONSerializable} into a JSON-encoded string.
     *
     * @param list The {@code List} containing the objects to serialize.
     *
     * @return A {@code String} containing the JSON representation of the list.
     */
    public static <T extends JSONSerializable> String writeJSONArray(List<T> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonMappingException e) {
            return "";
        } catch (JsonGenerationException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Serializes a {@code Set} containing objects that extend {@code JSONSerializable} into a JSON-encoded string.
     *
     * @param set The {@code Set} containing the objects to serialize.
     *
     * @return A {@code String} containing the JSON representation of the list.
     */
    public static <T extends JSONSerializable> String writeJSONArray(Set<T> set) {
        try {
            return mapper.writeValueAsString(set);
        } catch (JsonMappingException e) {
            return "";
        } catch (JsonGenerationException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Serializes a {@code List} containing objects that extend {@code JSONSerializable}, and outputs them using the specified
     * output stream.
     *
     * @param list   The {@code List} containing the objets to serialize.
     * @param writer The output stream to send the output to.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> void writeJSONArray(List<T> list, Writer writer) throws IOException {
        mapper.writeValue(writer, list);
    }

    /**
     * Serializes a {@code Set} containing objects that extend {@code JSONSerializable}, and outputs them using the specified
     * output stream.
     *
     * @param set    The {@code Set} containing the objets to serialize.
     * @param writer The output stream to send the output to.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> void writeJSONArray(Set<T> set, Writer writer) throws IOException {
        mapper.writeValue(writer, set);
    }

    /**
     * Serializes a {@code List} containing objects that extend {@code JSONSerializable}, and outputs them using the specified
     * output stream.
     *
     * @param list    The {@code List} containing the objets to serialize.
     * @param ostream The output stream to send the output to.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> void writeJSONArray(List<T> list, OutputStream ostream) throws IOException {
        mapper.writeValue(ostream, list);
    }

    /**
     * Serializes a {@code Set} containing objects that extend {@code JSONSerializable}, and outputs them using the specified
     * output stream.
     *
     * @param set     The {@code Set} containing the objets to serialize.
     * @param ostream The output stream to send the output to.
     *
     * @throws IOException
     */
    public static <T extends JSONSerializable> void writeJSONArray(Set<T> set, OutputStream ostream) throws IOException {
        mapper.writeValue(ostream, set);
    }

    /**
     * Reads a JSON-encoded string, creates, and returns the object it represents.
     *
     * @param json The JSON string to parse.
     *
     * @return The object represented by the JSON string.
     *
     * @throws IOException
     */
    public <T extends JSONSerializable> T readJSON(String json) throws IOException {
        return (T) mapper.readValue(json, this.getClass());
    }

    /**
     * Reads a JSON-encoded string, creates, and returns the object it represents.
     *
     * @param istream The input stream pointing to a JSON object to parse.
     *
     * @return The object represented by the JSON string.
     *
     * @throws IOException
     */
    public <T extends JSONSerializable> T readJSON(InputStream istream) throws IOException {
        return (T) mapper.readValue(istream, this.getClass());
    }

    /**
     * Writes this object to the specified {@code Writer}.
     *
     * @param writer The destination {@code Writer}.
     *
     * @throws IOException
     */
    public void writeJSON(Writer writer) throws IOException {
        mapper.writeValue(writer, this);
    }

    /**
     * Writes this object to the specified {@code OutputStream}.
     *
     * @param ostream The {@code OutputStream} to write to.
     *
     * @throws IOException
     */
    public void writeJSON(OutputStream ostream) throws IOException {
        mapper.writeValue(ostream, this);
    }

    /**
     * Writes this object to JSON and returns it as a {@code String}.
     *
     * @return The JSON string representation of this object.
     */
    public String writeJSON() {
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return writeJSON();
    }

    /**
     * Returns a formatted JSON representation of this object as a String.
     *
     * @param indent Whether or not to format the output.
     */
    public String toString(boolean indent) {
        if (indent) {
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            String out = toString();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            return out;
        } else {
            return toString();
        }
    }
}
