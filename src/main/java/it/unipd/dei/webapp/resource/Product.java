package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class Product extends Resource {
    private  int product_id;
    private int producer_id;
    private String product_name;
    private String picture;
    private int quantity;
    private int no_color;
    private String dimension;
    private String material;
    private int price;
    private String category;
    private String quality;


    public Product(int product_id, int producer_id, String product_name, String picture, int quantity, int no_color, String dimension, String material, int price, String category, String quality) {
        this.product_id = product_id;
        this.producer_id = producer_id;
        this.product_name = product_name;
        this.picture = picture;
        this.quantity = quantity;
        this.no_color = no_color;
        this.dimension = dimension;
        this.material = material;
        this.price = price;
        this.category = category;
        this.quality = quality;
    }
    public Product(int producer_id, String product_name, String picture, int quantity, int no_color, String dimension, String material, int price, String category, String quality) {
        this.producer_id = producer_id;
        this.product_name = product_name;
        this.picture = picture;
        this.quantity = quantity;
        this.no_color = no_color;
        this.dimension = dimension;
        this.material = material;
        this.price = price;
        this.category = category;
        this.quality = quality;
    }
    public int getProduct_id() {
        return product_id;
    }

    public int getProducer_id() {
        return producer_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getPicture() {
        return picture;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getNo_color() {
        return no_color;
    }

    public String getDimension() {
        return dimension;
    }

    public String getMaterial() {
        return material;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getQuality() {
        return quality;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("product_id", product_id);
        jg.writeNumberField("producer_id", producer_id);
        jg.writeStringField("product_name", product_name);
        jg.writeStringField("picture", picture);
        jg.writeNumberField("quantity", quantity);
        jg.writeNumberField("no_color", no_color);
        jg.writeStringField("dimension", dimension);
        jg.writeStringField(" material", material);
        jg.writeNumberField(" price", price);
        jg.writeStringField("category", category);
        jg.writeStringField("quality", quality);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("product_id", product_id);
        jsonObject.put("producer_id", producer_id);
        jsonObject.put("product_name", product_name);
        jsonObject.put("picture", picture);
        jsonObject.put("quantity", quantity);
        jsonObject.put("no_color", no_color);
        jsonObject.put("dimension", dimension);
        jsonObject.put(" material", material);
        jsonObject.put(" price", price);
        jsonObject.put("category", category);
        jsonObject.put("quality", quality);

        return  jsonObject;
    }
}