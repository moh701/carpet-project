package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCustomerByIDDAO {
    private final int id;
    private final Connection con;
    private static final String STATEMENT_CUSTOMER_GET_BY_ID = "SELECT name, surname, email_address FROM carpet.customer WHERE customer_id=?;";

    public GetCustomerByIDDAO(int id, Connection con) {
        this.id = id;
        this.con = con;
    }

    public Customer getCustomerByID() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_GET_BY_ID);
            stmnt.setInt(1, id);
            result = stmnt.executeQuery();

            Customer c = null;
            if (result.next()) {
                c = new Customer(result.getString(1), result.getString(2), result.getString(3));
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
