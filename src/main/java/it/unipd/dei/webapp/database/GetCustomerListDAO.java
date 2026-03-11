package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCustomerListDAO {
    private final Connection con;
    private static final String STATEMENT_CUSTOMER_GET_LIST = "SELECT name, surname, passwd, email_address, mobile_number, bank_account, address FROM carpet.customer;";

    public GetCustomerListDAO(Connection con) {
        this.con = con;
    }

    public List<Customer> getCustomerList() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_GET_LIST);
            result = stmnt.executeQuery();
            List<Customer> customers = new ArrayList<>();

            if (result.next()) {
                Customer c = new Customer(result.getString(1), result.getString(2), result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7));
                customers.add(c);
            }
            return customers;

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
