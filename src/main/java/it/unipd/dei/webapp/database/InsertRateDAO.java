package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Rate;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertRateDAO {
    private static final String STATEMENT_INSERT= "INSERT INTO carpet.rate(product_id, customer_id, grade) VALUES(?, ?, ?)";
    private final Connection con;
    //private final Product product;
    private final Rate rate;

    public InsertRateDAO(Connection con, Rate rate) {
        this.con = con;
        this.rate = rate;
    }

    public int insertRate()  throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_INSERT);
            stmnt.setInt(1, rate.getproduct_id());
            stmnt.setInt(2, rate.getCustomer_id());
            stmnt.setInt(2, rate.getgrade());
            int resultUpdate = stmnt.executeUpdate();

            if (resultUpdate==1){
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
