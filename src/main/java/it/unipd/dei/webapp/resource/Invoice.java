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

public class Invoice extends Resource {

    private int invoice_id;
    private int customer_id;
    private String serialno;
    private Timestamp datetime;

    public Invoice(int invoice_id)
    {
        this.invoice_id = invoice_id;
    }

    public Invoice(int invoice_id, int customer_id, String serialno, Timestamp datetime) {
        this.invoice_id = invoice_id;
        this.customer_id = customer_id;
        this.serialno = serialno;
        this.datetime = datetime;
    }

    public Invoice(int customer_id, String serialno, Timestamp datetime) {
        this.customer_id = customer_id;
        this.serialno = serialno;
        this.datetime = datetime;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getSerialno() {
        return serialno;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("invoice_id", invoice_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeStringField("serialno", serialno);
        jg.writeObjectField("datetime" , datetime);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    public static Invoice fromJSON(InputStream inputStream) throws IOException, JSONException {
        String dataString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        JSONObject jobj = new JSONObject(dataString);
        int customer_id = jobj.getInt("producer_id");
        String serialno = jobj.getString("serialno");
        Timestamp datetime = new Timestamp(jobj.getLong("datetime"));

        return new Invoice(customer_id, serialno, datetime);
    }
}