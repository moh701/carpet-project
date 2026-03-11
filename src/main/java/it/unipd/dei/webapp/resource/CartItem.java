package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class CartItem extends Resource {
    private HashMap<Product, Integer> items;

    public CartItem() {
        this.items = new HashMap<Product, Integer>();
    }
    public void addItem(Product product, int quantity){
        this.items.put(product, quantity);
    }
    public void updateQuantity(Product product, int newQuantity){
        this.items.put(product, newQuantity);
    }
    public void removeItem(Product product){
        this.items.remove(product);
    }
    public HashMap<Product, Integer> getItems(){
        return this.items;
    }

    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        JSONObject jo = new JSONObject(items);
        jg.writeStringField("cart", jo.toString());
        jg.writeEndObject();
        jg.flush();
    }

}
