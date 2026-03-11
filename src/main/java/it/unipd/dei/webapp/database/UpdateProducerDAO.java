package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProducerDAO {
    private static final String STATEMENT_UPDATE_Producer = "UPDATE carpet.producer SET email_address=?, name=?, surname=?, mobile_number=?, bank_account=?, brand=?, address=? WHERE email_address=?;";
    private final Connection con;
    private Producer p;

    public UpdateProducerDAO(Connection con, Producer prd) {
        this.con = con;
        this.p = prd;
    }

    public int updateProducerNoPassword() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_UPDATE_Producer);
            stmnt.setString(1, p.getEmail_address());
            stmnt.setString(2, p.getName());
            stmnt.setString(3, p.getSurname());
            stmnt.setString(4, p.getMobile_number());
            stmnt.setString(5, p.getBank_account());
            stmnt.setString(6, p.getBrand());
            stmnt.setString(7, p.getAddress());
            stmnt.setString(8, p.getEmail_address());

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
