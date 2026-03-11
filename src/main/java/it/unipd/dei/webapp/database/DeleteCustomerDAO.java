package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Producer;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCustomerDAO {
    private final String email;
    private final Connection con;
    private static final String STATEMENT_CUSTOMER_DELETE = "DELETE FROM carpet.customer WHERE email_address=?;";

    public DeleteCustomerDAO(String email, Connection con) {
        this.email = email;
        this.con = con;
    }

    public int deleteCustomer() throws SQLException, NamingException{
        PreparedStatement stmnt = null;
        try {
            Customer c = getCustomer();
            if (c == null) {
                return -1;
            }
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_DELETE);
            stmnt.setString(1, email);
            int result = stmnt.executeUpdate();
            if (result == 1) {
                return 0;
            } else {
                return -2;
            }
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
    public Customer getCustomer() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            String query = "SELECT name, surname, email_address FROM carpet.customer WHERE email_address=?;";
            stmnt = con.prepareStatement(query);
            stmnt.setString(1, email);
            result = stmnt.executeQuery();

            Customer c = null;
            if (result.next()) {
                c = new Customer(result.getString(1), result.getString(2), result.getString(3));
            }
            return c;
        }
        finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
