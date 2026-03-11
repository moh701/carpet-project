package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.*;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Resource{
    protected static final JsonFactory JSON_FACTORY;

    static {
        JSON_FACTORY = new JsonFactory();
        JSON_FACTORY.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        JSON_FACTORY.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    }
    public abstract void toJSON(final OutputStream out) throws IOException;
}
