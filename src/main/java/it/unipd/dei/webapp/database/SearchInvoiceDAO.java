package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Invoice;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchInvoiceDAO {
    private static final String STATEMENT_Invoice_Search = "SELECT * FROM FROM carpet.invoice WHERE datetime>=TO_DATE(?, 'YYYY/MM/DD')";

    private final Connection con;
    private final String datetime;
    private final String email_address;
    private final String serialno;

    public SearchInvoiceDAO(Connection con, String datetime, String email_address, String serialno) {
        this.con = con;
        this.datetime = datetime;
        this.email_address = email_address;
        this.serialno = serialno;
    }

    public List<Invoice> searchInvoice() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<Invoice> invoiceList = new ArrayList<Invoice>();

        try {
            stmnt = con.prepareStatement(STATEMENT_Invoice_Search);
            stmnt.setString(1, datetime);

            /*Customer c= CustomerDAO.getCustomer(email_address);
            int customer_id = c.getCustomer_id();
            if (customer_id>0) {
                STATEMENT_Invoice_Search += " AND customer_id=?";
                stmnt.setInt(2, customer_id);
            }
            if(serialno!= null && serialno!= ""){
                STATEMENT_Invoice_Search += " AND serialno=?";
                stmnt.setString(3, serialno);
            }*/

            result = stmnt.executeQuery();

            while (result.next()) {
                Invoice invoice = null;

                invoice = new Invoice(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getTimestamp(4));

                invoiceList.add(invoice);
            }
            return invoiceList;
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }


}
