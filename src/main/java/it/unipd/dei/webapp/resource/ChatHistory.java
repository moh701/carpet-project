package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class ChatHistory extends Resource{
    private int chat_id;
    private int producer_id;
    private int customer_id;
    private Timestamp datetime;
    private String message_content;

    public ChatHistory(int chat_id, int producer_id, int customer_id, Timestamp datetime, String message_content) {
        this.chat_id = chat_id;
        this.producer_id = producer_id;
        this.customer_id = customer_id;
        this.datetime = datetime;
        this.message_content = message_content;
    }

    public ChatHistory(int producer_id, int customer_id, Timestamp datetime, String message_content) {
        this.producer_id = producer_id;
        this.customer_id = customer_id;
        this.datetime = datetime;
        this.message_content = message_content;
    }



    public int getChat_id() {
        return chat_id;
    }

    public int getProducer_id() {
        return producer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public String getMessage_content() {
        return message_content;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("chat_id", chat_id);
        jg.writeNumberField("producer_id", producer_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeObjectField("datetime", datetime);
        jg.writeStringField("message_content", message_content);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    public static ChatHistory fromJSON(InputStream inputStream) throws IOException, JSONException {
        String dataString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        JSONObject jobj = new JSONObject(dataString);
        int producer_id = jobj.getInt("producer_id");
        int customer_id = jobj.getInt("customer_id");
        Timestamp datetime = new Timestamp(jobj.getLong("datetime"));
        String message_content = jobj.getString("message_content");

        return new ChatHistory(producer_id, customer_id, datetime, message_content);
    }
}