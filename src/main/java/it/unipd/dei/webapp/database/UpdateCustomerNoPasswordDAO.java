package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomerNoPasswordDAO {
    private final Customer c;
    private final Connection con;
    private final String email;
    private static final String STATEMENT_CUSTOMER_UPDATE_NO_PASSWORD = "UPDATE carpet.customer SET email_address=?, name=?, surname=?, mobile_number=?, bank_account=? WHERE email_address=?;";

    public UpdateCustomerNoPasswordDAO(Customer c, Connection con, String email) {
        this.c = c;
        this.con = con;
        this.email = email;
    }

    public int updateCustomerNoPassword() throws SQLException{
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_UPDATE_NO_PASSWORD);
            stmnt.setString(1, email);
            stmnt.setString(2, c.getName());
            stmnt.setString(3, c.getSurname());
            stmnt.setString(4, c.getMobile_number());
            stmnt.setString(5, c.getBank_account());
            stmnt.setString(6, email);

            int updateResult = stmnt.executeUpdate();
            if (updateResult == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
