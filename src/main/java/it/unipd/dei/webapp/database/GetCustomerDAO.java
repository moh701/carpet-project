package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCustomerDAO {
    private final String email;
    private final Connection con;
    private static final String STATEMENT_CUSTOMER_GET = "SELECT * FROM carpet.customer WHERE email_address=?;";

    public GetCustomerDAO(String email, Connection con) {
        this.email = email;
        this.con = con;
    }

    public Customer getCustomer() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_GET);
            stmnt.setString(1, email);
            result = stmnt.executeQuery();

            Customer c = null;
            if (result.next()) {
                c = new Customer(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8));
            }
            return c;
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
