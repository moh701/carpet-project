package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomerBankAccountDAO {
    private final Customer c;
    private final String bank_account;
    private final Connection con;

    private static final String STATEMENT_CUSTOMER_UPDATE_BANK_ACCOUNT = "UPDATE carpet.customer SET bank_account=? WHERE email_address=?;";

    public UpdateCustomerBankAccountDAO(Customer c, String bank_account, Connection con) {
        this.c = c;
        this.bank_account = bank_account;
        this.con = con;
    }

    public int updateCustomerBankAccount() throws SQLException{
        PreparedStatement stmnt = null;
        try{
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_UPDATE_BANK_ACCOUNT);
            stmnt.setString(1, bank_account);
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
