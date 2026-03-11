package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class LikeDislike extends Resource{
    private int comment_id;
    private int customer_id;
    private boolean interest;

    public LikeDislike(int comment_id, int customer_id, boolean interest){
        this.comment_id = comment_id;
        this.customer_id = customer_id;
        this.interest = interest;
    }

    public int getComment_id() {
        return comment_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public boolean isInterest() {
        return interest;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("comment_id", comment_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeBooleanField("interest", interest);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}