package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteProducerDAO {
    private static final String STATEMENT_DELETE_Producer = "DELETE FROM carpet.producer WHERE email_address=?;";
    private final Connection con;
    private final String email;

    public DeleteProducerDAO(Connection con, String email) {
        this.con = con;
        this.email = email;
    }

    public int deleteProducer() throws SQLException, NamingException {
        PreparedStatement stmnt = null;

        try {
            Producer p = getProducer();
            if (p == null) {
                return -1;
            }

            stmnt = con.prepareStatement(STATEMENT_DELETE_Producer);
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

    public Producer getProducer() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            String query = "SELECT name, surname, email_address FROM carpet.producer WHERE email_address=?;";
            stmnt = con.prepareStatement(query);
            stmnt.setString(1, email);
            result = stmnt.executeQuery();

            Producer p = null;
            if (result.next()) {
                p = new Producer(result.getString(1), result.getString(2), result.getString(3));
            }
            return p;
        }
        finally {
        if (stmnt != null) {
            stmnt.close();
        }
        con.close();
        }
    }

}
