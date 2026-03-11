package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

public class Comment extends Resource {
    private  int comment_id;
    private int customer_id;
    private int product_id;
    private String comment_content;
    private int reply_no;
    private int like_no;
    private int dislike_no;
    private Timestamp datetime;


    public Comment(int comment_id, int customer_id, int product_id, String comment_content, int reply_no, int like_no, int dislike_no, Timestamp datetime) {
        this.comment_id = comment_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.comment_content = comment_content;
        this.reply_no = reply_no;
        this.like_no = like_no;
        this.dislike_no = dislike_no;
        this.datetime = datetime;
    }
    public Comment(int product_id, String comment_content, int reply_no, int like_no, int dislike_no, Timestamp datetime) {
        this.product_id = product_id;
        this.comment_content = comment_content;
        this.reply_no = reply_no;
        this.like_no = like_no;
        this.dislike_no = dislike_no;
        this.datetime = datetime;
    }

    public int getComment_id() {
        return comment_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public int getReply_no() {
        return reply_no;
    }

    public int getLike_no() {
        return like_no;
    }

    public int getDislike_no() {
        return dislike_no;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public int getProduct_id() {
        return product_id;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("comment_id", comment_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeNumberField("product_id", product_id);
        jg.writeStringField("comment_content ", comment_content);
        jg.writeNumberField(" reply_no ", reply_no);
        jg.writeNumberField("like_no", like_no);
        jg.writeNumberField("dislike_no", dislike_no);
        jg.writeObjectField("datetime", datetime);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}