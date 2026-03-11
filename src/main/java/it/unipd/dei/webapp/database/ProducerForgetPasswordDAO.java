package it.unipd.dei.webapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProducerForgetPasswordDAO {
    private final Connection connection;
    private final String email;
    private static final String STATEMENT_PRODUCER_EMAIL = "SELECT * FROM carpet.producer WHERE email_address=?;";
    public ProducerForgetPasswordDAO(String email, Connection connection) {
        this.email = email;
        this.connection = connection;
    }
    public int producerForgetPassword() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = connection.prepareStatement(STATEMENT_PRODUCER_EMAIL);
            stmnt.setString(1, email);
            result = stmnt.executeQuery();
            int res = -1;

            if (result.next()) {
                res = 0;
            }
            return res;

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            connection.close();
        }
    }

}
