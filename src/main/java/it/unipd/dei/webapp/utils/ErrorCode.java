package it.unipd.dei.webapp.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public enum ErrorCode {

    PRODUCER_NOT_FOUND(-100, HttpServletResponse.SC_BAD_REQUEST,"producer is not exist."),
    CHAT_ALREADY_PRESENT(-101, HttpServletResponse.SC_NOT_FOUND, "chat is already present."),
    EMPTY_INPUT_FIELDS(-102, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
    EMAIL_MISSING(-103, HttpServletResponse.SC_BAD_REQUEST, "Email missing"),
    PASSWORD_MISSING(-104, HttpServletResponse.SC_BAD_REQUEST, "Password missing"),
    WRONG_CREDENTIALS(-105, HttpServletResponse.SC_BAD_REQUEST, "Submitted credentials are wrong"),
    DELETED_USER_NOT_PRESENT(-106, HttpServletResponse.SC_NOT_FOUND, "User to be deleted not found."),
    DIFFERENT_PASSWORDS(-107, HttpServletResponse.SC_CONFLICT, "different passwords"),
    MAIL_ALREADY_USED(-108, HttpServletResponse.SC_CONFLICT, "mail already used"),
    UNRECOGNIZED_ROLE(-109, HttpServletResponse.SC_BAD_REQUEST, "Unrecognized role"),
    PARK_ALREADY_PRESENT(-110, HttpServletResponse.SC_CONFLICT, "The park name has already been used."),
    MODEL_ALREADY_PRESENT(-111, HttpServletResponse.SC_CONFLICT, "The model name has already been used."),
    MODEL_NOT_FOUND(-112, HttpServletResponse.SC_NOT_FOUND, "Model not found."),
    WRONG_REST_FORMAT(-113, HttpServletResponse.SC_BAD_REQUEST, "Wrong rest request format."),
    CUSTOMER_NOT_FOUND(-114, HttpServletResponse.SC_CONFLICT, "customer is not exist "),
    PRODUCT_NOT_EXISTED(-115, HttpServletResponse.SC_NOT_FOUND, "Product does not exist."),
    SERIALNUMBER_NOT_FOUND(-116, HttpServletResponse.SC_CONFLICT, "SERIALNUMBER is not match"),
    USER_NOT_FOUND(-117, HttpServletResponse.SC_NOT_FOUND, "User not found."),
    NO_CHAT_HISTORY(-118, HttpServletResponse.SC_BAD_REQUEST, "There is no chat history between these users!"),
    DEVICE_NOT_FOUND(-119, HttpServletResponse.SC_NOT_FOUND, "Device not found"),
    INVOCE_NOT_FOUND(-120,  HttpServletResponse.SC_BAD_REQUEST, "The INVOICE IS NOT FOUND."),
    WRONG_FORMAT(-121, HttpServletResponse.SC_BAD_REQUEST,"Wrong format of the request."),

    OPERATION_UNKNOWN(-200, HttpServletResponse.SC_BAD_REQUEST, "Operation unknown."),
    METHOD_NOT_ALLOWED(-500, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "The method is not allowed"),
    INVOICE_ALREADY_PRESENT(-750, HttpServletResponse.SC_UNAUTHORIZED, "The invoice is not exist!!!!"),
    TOKEN_EXPIRED(-751, HttpServletResponse.SC_UNAUTHORIZED, "The token has expired."),
    INTERNAL_ERROR(-999, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error");


    private final int errorCode;
    private final int httpCode;
    private final String errorMessage;

    ErrorCode(int errorCode, int httpCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHTTPCode() {
        return httpCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        data.put("code", errorCode);
        data.put("message", errorMessage);
        JSONObject info = new JSONObject();
        info.put("error", data);
        return info;
    }
}