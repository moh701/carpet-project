package it.unipd.dei.webapp.resource;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class Reply extends Resource{
    private int reply_id;
    private int replied_id;

    public Reply(int reply_id, int replied_id) {
        this.reply_id = reply_id;
        this.replied_id = replied_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public int getReplied_id() {
        return replied_id;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("reply_id", reply_id);
        jg.writeNumberField("replied_id", replied_id);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}