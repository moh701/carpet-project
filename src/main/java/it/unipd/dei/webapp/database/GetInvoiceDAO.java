package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Invoice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetInvoiceDAO {
    private static final String STATEMENT_INVOICE_GET = "SELECT * FROM FROM carpet.invoice WHERE invoice_id=?)";
    private final Connection con;
    private final Invoice invoice;

    public GetInvoiceDAO(Connection con, Invoice invoice) {
        this.con = con;
        this.invoice = invoice;
    }

    public Invoice getInvoicebyID() throws SQLException{

        PreparedStatement stmnt = null;
        ResultSet result = null;
        Invoice inv = null;

        try {
            stmnt = con.prepareStatement(STATEMENT_INVOICE_GET);
            stmnt.setInt(1,invoice.getInvoice_id());
            result = stmnt.executeQuery();

            while (result.next()) {
                inv = new Invoice(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getTimestamp(4));
            }
            return inv;
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
