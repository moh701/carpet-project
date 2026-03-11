package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProducerByIDDAO {
    private static final String STATEMENT_Producer_GET_ID = "SELECT name, surname, email_address FROM carpet.producer WHERE producer_id=?;";
    private final Connection con;
    private int producer_id;

    public GetProducerByIDDAO(Connection con, int producerID) {
        this.con = con;
        this.producer_id = producerID;
    }

    public Producer getProducerByID() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_Producer_GET_ID);
            stmnt.setInt(1, producer_id);
            result = stmnt.executeQuery();

            Producer p = null;
            if (result.next()) {
                p = new Producer(result.getString(1), result.getString(2), result.getString(3));
            }
            return p;
        }
        finally
        {
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
