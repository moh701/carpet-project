package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class Customer extends Resource {
    private int customer_id;
    private String name;
    private String surname;
    private String password;
    private String email_address;
    private String mobile_number;
    private String address;
    private String bank_account;



    public Customer(String name, String surname, String password, String email_address, String mobile_number, String address, String bank_account) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email_address = email_address;
        this.mobile_number = mobile_number;
        this.address = address;
        this.bank_account = bank_account;
    }

    public Customer(String name, String surname, String password, String email_address, String mobile_number) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email_address = email_address;
        this.mobile_number = mobile_number;
    }

    public Customer(int customer_id, String name, String surname, String password, String email_address, String mobile_number, String address, String bank_account) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email_address = email_address;
        this.mobile_number = mobile_number;
        this.address = address;
        this.bank_account = bank_account;
        this.customer_id = customer_id;
    }

    //added for CustomerDAO class
    public Customer(String name, String surname, String email_address) {
        this.name = name;
        this.surname = surname;
        this.email_address = email_address;

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getBank_account() {
        return bank_account;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeStringField("name", name);
        jg.writeStringField("surname", surname);
        jg.writeStringField("password", password);
        jg.writeStringField("email_address", email_address);
        jg.writeStringField("address", address);
        jg.writeStringField("mobile_number ", mobile_number);
        jg.writeStringField(" bank_account ", bank_account);
        jg.writeNumberField("customer_id", customer_id);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}