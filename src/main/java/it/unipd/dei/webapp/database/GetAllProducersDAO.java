package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllProducersDAO {
    private static final String STATEMENT_AllProducer_GET ="SELECT * FROM carpet.producers";
    private final Connection con;

    public GetAllProducersDAO(Connection con) {
        this.con = con;
    }

    public List<Producer> getAllProducers() throws SQLException, NamingException {
        List<Producer> producers = new ArrayList<>();
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_AllProducer_GET);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Producer producer = new Producer(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("password"),
                        rs.getString("mobileNumber"),
                        rs.getString("bankAccount"),
                        rs.getString("brand"),
                        rs.getString("address")
                );
                producers.add(producer);
            }
            return producers;

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
