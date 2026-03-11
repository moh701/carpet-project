package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProducerDAO {
    private final String email;
    private final Connection con;
    private static final String STATEMENT_PRODUCER_GET = "SELECT * FROM carpet.producer WHERE email_address=?;";

    public GetProducerDAO(String email, Connection con) {
        this.email = email;
        this.con = con;
    }

    public Producer getProducer() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_PRODUCER_GET);
            stmnt.setString(1, email);
            result = stmnt.executeQuery();

            Producer p = null;
            if (result.next()) {
                p = new Producer(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9));
            }
            return p;
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
