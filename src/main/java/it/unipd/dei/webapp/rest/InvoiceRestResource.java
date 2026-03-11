package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.database.CreateInvoiceDAO;
import it.unipd.dei.webapp.database.GetInvoiceDAO;
import it.unipd.dei.webapp.resource.Invoice;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class InvoiceRestResource extends RestResource {
    /**
     * Creates a new REST resource.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param ds  the dataSource for the connection.
     */
    public InvoiceRestResource(HttpServletRequest req, HttpServletResponse res, DataSource ds) {
        super(req, res, ds);
    }

    public void insertInvoice() throws IOException {
        try {
            Invoice invoice = Invoice.fromJSON(req.getInputStream());

            int insertResult = new CreateInvoiceDAO(super.ds.getConnection(), invoice).createInvoice(); //shili edited

            if (insertResult == -2) {
                ErrorCode ec = ErrorCode.INVOICE_ALREADY_PRESENT;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (insertResult == -3) {
                ErrorCode ec = ErrorCode.CUSTOMER_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (insertResult == -4) {
                ErrorCode ec = ErrorCode.SERIALNUMBER_NOT_FOUND;
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


        } catch (SQLException /*| NamingException */ e) {//shili edited
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
            logger.error("stacktrace:", e);
        }
    }

    public void getInvoice() throws IOException {

        try {
            String op = req.getRequestURI();
            String[] tokens = op.split("/");
            // URL : ****.com/invoice/view/id
            int invoiceId = Integer.parseInt(tokens[3]); //shili edited
            Invoice inv = new Invoice(invoiceId); //shili edited
            Invoice invoice = new GetInvoiceDAO(super.ds.getConnection(),inv).getInvoicebyID(); //shili edited

            if (invoice == null) {
                ErrorCode ec = ErrorCode.INVOCE_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                res.setStatus(HttpServletResponse.SC_OK);
                invoice.toJSON(res.getOutputStream());
            }

        } catch (SQLException /*| NamingException*/ e) { //shili edited
            logger.error("stacktrace:", e);
            writeError(res, ErrorCode.INTERNAL_ERROR);
        }


    }

}

