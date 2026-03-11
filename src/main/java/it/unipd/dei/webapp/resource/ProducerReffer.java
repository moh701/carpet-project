package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class ProducerReffer extends Resource{
    private int referer_id;
    private int refered_id;

    public ProducerReffer(int referer_id, int refered_id) {
        this.referer_id = referer_id;
        this.refered_id = refered_id;
    }

    public int getReferer_id() {
        return referer_id;
    }

    public int getRefered_id() {
        return refered_id;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("referer_id", referer_id);
        jg.writeNumberField("refered_id", refered_id);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}