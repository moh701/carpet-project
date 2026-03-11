package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class Object extends Resource{
    private  int product_id;
    private String serialnumber;

    public Object(int product_id, String serialnumber) {
        this.product_id = product_id;
        this.serialnumber = serialnumber;
    }
    public Object( String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeStringField("serialnumber", serialnumber);
        jg.writeNumberField("product_id", product_id);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}
