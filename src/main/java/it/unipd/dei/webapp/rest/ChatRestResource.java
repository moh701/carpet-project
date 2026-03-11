package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.database.GetChatHistoryDAO;
import it.unipd.dei.webapp.database.GetCustomerByIDDAO;
import it.unipd.dei.webapp.database.GetProducerByIDDAO;
import it.unipd.dei.webapp.database.InsertChatHistoryDAO;
import it.unipd.dei.webapp.resource.ChatHistory;
import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class ChatRestResource extends RestResource {
    /**
     * Creates a new REST resource.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param ds  the dataSource for the connection.
     */
    public ChatRestResource(HttpServletRequest req, HttpServletResponse res, DataSource ds) {
        super(req, res, ds);
    }


    public void inserChat() throws IOException {
        try {
            ChatHistory chatHistory = ChatHistory.fromJSON(req.getInputStream());
            if (chatHistory.getMessage_content().isEmpty()) {
                logger.error("EMPTY FIELDS in CHAT MESSAGE");
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {

                int insertResult = new InsertChatHistoryDAO(ds.getConnection(), chatHistory).insertChatHistory();

                if (insertResult == -2) {
                    ErrorCode ec = ErrorCode.CHAT_ALREADY_PRESENT;
                    res.setStatus(ec.getHTTPCode());
                    res.getWriter().write(ec.toJSON().toString());
                } else if (insertResult == -3) {
                    ErrorCode ec = ErrorCode.CUSTOMER_NOT_FOUND;
                    res.setStatus(ec.getHTTPCode());
                    res.getWriter().write(ec.toJSON().toString());
                } else if (insertResult == -4) {
                    ErrorCode ec = ErrorCode.PRODUCER_NOT_FOUND;
                    res.setStatus(ec.getHTTPCode());
                    res.getWriter().write(ec.toJSON().toString());
                } else if (insertResult == -1) {
                    ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                    res.setStatus(ec.getHTTPCode());
                    res.getWriter().write(ec.toJSON().toString());
                } else {

                    //right insert in the database
                    JSONObject resJSON = new JSONObject();
                    res.setStatus(HttpServletResponse.SC_OK);
                    resJSON.put("result", "successfull");
                    res.getWriter().write(resJSON.toString());
                }
            }

        } catch (SQLException e) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
            logger.error("stacktrace:", e);
        }
    }


    public void getChatHistory() throws IOException {
        try {
            String op = req.getRequestURI();
            String[] tokens = op.split("/");
            // URL : ****.com/chat/history/producer/customer
            int producerId = Integer.parseInt(tokens[3]);
            int customerId = Integer.parseInt(tokens[4]);
            Producer p = new GetProducerByIDDAO(super.ds.getConnection(),producerId).getProducerByID(); //ProducerDAO.getProducerByID(producerId); //shili edited
            Customer c = new GetCustomerByIDDAO(customerId, ds.getConnection()).getCustomerByID();

            if (p == null || c == null) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                ChatHistory chatHistory = new GetChatHistoryDAO(ds.getConnection(), c, p).getChatHistory();
                if (chatHistory == null) {
                    ErrorCode ec = ErrorCode.NO_CHAT_HISTORY;
                    res.setStatus(ec.getHTTPCode());
                    res.getWriter().write(ec.toJSON().toString());
                } else {
                    res.setStatus(HttpServletResponse.SC_OK);
                    chatHistory.toJSON(res.getOutputStream());
                }
            }

        } catch (SQLException | NamingException e) {
            logger.error("stacktrace:", e);
            writeError(res, ErrorCode.INTERNAL_ERROR);
        }
    }
}
