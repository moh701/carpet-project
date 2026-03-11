package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class Rate extends Resource{
    private int product_id;
    private int customer_id;
    private int grade;

    public Rate(int product_id, int customer_id, int grade){
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.grade = grade;
    }

    public int getproduct_id() {
        return product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getgrade() {
        return grade;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("product_id", product_id);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeNumberField("grade", grade);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}