package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

public class ReturnReciept extends Resource{
    private int return_reciept_id;
    private int customer_id;
    private String serialno;
    private Timestamp datetime;
    private String reason;


    public ReturnReciept(int return_reciept_id, int customer_id, String serialno, Timestamp datetime, String reason) {
        this.return_reciept_id = return_reciept_id;
        this.customer_id = customer_id;
        this.serialno = serialno;
        this.datetime = datetime;
        this.reason = reason;
    }
    public ReturnReciept(int customer_id, String serialno, Timestamp datetime, String reason) {
        this.customer_id = customer_id;
        this.serialno = serialno;
        this.datetime = datetime;
        this.reason = reason;
    }

    public int getReturn_reciept_id() {
        return return_reciept_id;
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

    public String getReason() {
        return reason;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("return_reciept_id", return_reciept_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeStringField("serialno", serialno);
        jg.writeObjectField("datetime ", datetime);
        jg.writeStringField("reason",  reason);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}