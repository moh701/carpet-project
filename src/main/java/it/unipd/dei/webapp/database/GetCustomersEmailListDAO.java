package it.unipd.dei.webapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCustomersEmailListDAO {
    private final Connection con;
    private static final String STATEMENT_CUSTOMER_GET_EMAIL_LIST = "SELECT email_address FROM carpet.customer;";

    public GetCustomersEmailListDAO(Connection con) {
        this.con = con;
    }

    public List<String> getCustomersEmailList() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_GET_EMAIL_LIST);
            result = stmnt.executeQuery();
            List<String> emails = new ArrayList<>();

            while (result.next()) {
                emails.add(result.getString(1));
            }
            return emails;

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
