package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

public class BargainHistory extends Resource {
    private int bargain_id;
    private int customer_id;
    private int product_id;
    private int price;
    private Timestamp datetime;
    private int bargain_result;

    public BargainHistory(int bargain_id, int customer_id, int product_id, int price, Timestamp datetime, int bargain_result) {
        this.bargain_id = bargain_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.price = price;
        this.datetime = datetime;
        this.bargain_result = bargain_result;
    }

    public BargainHistory(int customer_id, int product_id, int price, Timestamp datetime) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.price = price;
        this.datetime = datetime;
    }

    public int getBargain_id() {
        return bargain_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getPrice() {
        return price;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public int getBargain_result() {
        return bargain_result;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("bargain_id", bargain_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeNumberField("product_id", product_id);
        jg.writeNumberField("price", price);
        jg.writeObjectField("datetime", datetime);
        jg.writeNumberField("bargain_result", bargain_result);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    public JSONObject toJSON(){
        JSONObject bhJSON = new JSONObject();
        bhJSON.put("bargain_id", bargain_id);
        bhJSON.put("customer_id", customer_id);
        bhJSON.put("product_id", product_id);
        bhJSON.put("price", price);
        bhJSON.put("datetime", datetime);
        bhJSON.put("bargain_result", bargain_result);
        return bhJSON;
    }
}