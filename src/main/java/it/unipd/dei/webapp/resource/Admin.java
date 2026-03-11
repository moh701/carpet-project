package it.unipd.dei.webapp.resource;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;


public class Admin  extends Resource {
    private int admin_id;
    private String email_address;
    private String password;
    private String role;

    public Admin(String email, String password, String role) {
        this.email_address = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {return role;}


    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeNumberField("admin_id", admin_id);
        jg.writeStringField("password", password);
        jg.writeStringField("email_address", email_address);
        jg.writeStringField(" role ", role);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

}
