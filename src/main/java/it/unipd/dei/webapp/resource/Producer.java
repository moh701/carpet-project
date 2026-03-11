package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.io.OutputStream;

public class Producer extends Resource{
    private int producer_id;
    private String name;
    private String surname;
    private String password;
    private String email_address;
    private String mobile_number;
    private String bank_account;
    private String address;
    private String brand;


    // added for ProducerDAO class

    public Producer(String name, String surname, String email_address) {
        this.email_address = email_address;
        this.name = name;
        this.surname = surname;
    }

    public Producer(int producer_id, String name, String surname, String password, String email_address, String mobile_number, String bank_account, String address, String brand) {
        this.producer_id = producer_id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email_address = email_address;
        this.mobile_number = mobile_number;
        this.bank_account = bank_account;
        this.address = address;
        this.brand = brand;
    }

    public Producer(String name, String surname, String password, String email_address, String mobile_number, String bank_account, String brand, String address){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email_address =  email_address;
        this.mobile_number = mobile_number;
        this.bank_account =  bank_account;
        this.brand = brand;
        this.address = address;
    }

    public Producer(String name, String surname, String password, String email_address, String mobile_number, String brand){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email_address =  email_address;
        this.mobile_number = mobile_number;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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

    public int getProducer_id() {
        return producer_id;
    }

    public String getAddress() {
        return address;
    }

    public String getBrand() {
        return brand;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeStringField("name", name);
        jg.writeStringField("surname", surname);
        jg.writeStringField("passwd", password);
        jg.writeStringField("email_address", email_address);
        jg.writeStringField("mobile_number ", mobile_number );
        jg.writeStringField("bank_account", bank_account );
        jg.writeNumberField("producer_id", producer_id);
        jg.writeStringField("address", address);
        jg.writeStringField("brand", brand);
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}