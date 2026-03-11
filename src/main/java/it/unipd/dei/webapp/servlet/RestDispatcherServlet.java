package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.rest.ChatRestResource;
import it.unipd.dei.webapp.rest.InvoiceRestResource;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RestDispatcherServlet extends AbstractServlet {


    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String op = req.getRequestURI();
        if (processChat(req, res)) {
            return;
        }
        if (processInvice(req, res)) {
            return;
        }
        writeError(res, ErrorCode.OPERATION_UNKNOWN);
        logger.warn("requested op " + op);
    }

    private boolean processChat(HttpServletRequest req, HttpServletResponse res)  throws IOException {
        String op = req.getRequestURI();
        if (!op.contains("chat")) {
            return false;
        }

        op = op.substring(op.lastIndexOf("chat"));
        String[] tokens = op.split("/");
        // First one is "chat"
        // Second one is "get" or "insert"

        if (tokens[1].equals("get")) {
            if (req.getMethod().equals("GET")) {
                new ChatRestResource(req, res, getDataSource()).getChatHistory();
            } else {
                writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
            }
        } else if (tokens[1].equals("inset")) {
            if (req.getMethod().equals("POST")) {
                new ChatRestResource(req, res, getDataSource()).inserChat();
            } else {
                writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean processInvice(HttpServletRequest req, HttpServletResponse res)  throws IOException{
        String op = req.getRequestURI();
        if (!op.contains("invoice")) {
            return false;
        }

        op = op.substring(op.lastIndexOf("invoice"));
        String[] tokens = op.split("/");
        // First one is "invoice"
        // Second one is "get" or "insert"

        if (tokens[1].equals("get")) {
            if (req.getMethod().equals("GET")) {
                new InvoiceRestResource(req, res, getDataSource()).getInvoice();
            } else {
                writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
            }
        } else if (tokens[1].equals("inset")) {
            if (req.getMethod().equals("POST")) {
                new InvoiceRestResource(req, res, getDataSource()).insertInvoice();
            } else {
                writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
            }
        } else {
            return false;
        }
        return true;
    }


}
