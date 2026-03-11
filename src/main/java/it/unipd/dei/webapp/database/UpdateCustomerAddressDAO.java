package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomerAddressDAO {
    private final Customer c;
    private final String address;
    private final Connection con;

    private static final String STATEMENT_CUSTOMER_UPDATE_BANK_ACCOUNT = "UPDATE carpet.customer SET address=? WHERE email_address=?;";

    public UpdateCustomerAddressDAO(Customer c, String address, Connection con) {
        this.c = c;
        this.address = address;
        this.con = con;
    }

    public int updateCustomerAddress() throws SQLException{
        PreparedStatement stmnt = null;
        try{
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_UPDATE_BANK_ACCOUNT);
            stmnt.setString(1, address);
            stmnt.setString(2, c.getEmail_address());

            int updateResult = stmnt.executeUpdate();
            if (updateResult == 1) {
                return 0;
            } else {
                return -1;
            }
        }
        finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
