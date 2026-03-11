package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Invoice;


import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateInvoiceDAO {
    private static final String STATEMENT_INVOICE_CREATION = "INSERT INTO carpet.invoice (customer_id, serialno, datetime) VALUES (?, ?, ?) "; ;
    private final Connection con;
    private final Invoice invoice;

    public CreateInvoiceDAO(Connection con, Invoice inv) {
        this.con = con;
        this.invoice = inv;
    }

    public int createInvoice() throws SQLException{
        PreparedStatement stmnt = null;

        try{
            stmnt = con.prepareStatement(STATEMENT_INVOICE_CREATION);
            stmnt.setInt(1, invoice.getCustomer_id());
            stmnt.setString(2, invoice.getSerialno());
            stmnt.setTimestamp(3, invoice.getDatetime());

            int result = stmnt.executeUpdate();

            if(result == 1){
                return 0;
            } else {
                return -1;
            }
        } finally{
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }


}
