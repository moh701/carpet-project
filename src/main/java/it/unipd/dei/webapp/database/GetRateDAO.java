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

public class GetRateDAO {
    private static final String STATEMENT_RATE_GET = "SELECT * FROM carpet.rate WHERE product_id=(SELECT product_id FROM carpet.product WHERE product_name = ?)";
    private final Connection con;
    //private final Product product;
    private final String product_name;

    public GetRateDAO(Connection con, String product_name) {
        this.con = con;
        this.product_name = product_name;
    }
    //String product_name

    public Rate getRate() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Rate r=null;

        try {
            stmnt = con.prepareStatement(STATEMENT_RATE_GET);
            stmnt.setString(1, product_name);
            result = stmnt.executeQuery();

            if (result.next()) {
                r = new Rate(result.getInt(1), result.getInt(2), result.getInt(3));
            }
            return r;
        }
        finally {
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
