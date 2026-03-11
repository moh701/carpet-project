package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Rate;
import it.unipd.dei.webapp.resource.Product;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetAllRateDAO {
    private static final String STATEMENT_ALLRATE_GET = "SELECT * FROM FROM carpet.rate WHERE productId=?)";
    private final Connection con;
    //private final Product product;
    private final int productID;

    public GetAllRateDAO(Connection con, /*Product prd,*/ int productID) {
        this.con = con;
        this.productID = productID;
    }

    public List<Rate> getAllRateByProductId() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<Rate> rateList = new ArrayList<Rate>();

        try {
            stmnt = con.prepareStatement(STATEMENT_ALLRATE_GET);
            stmnt.setInt(1,productID);
            result = stmnt.executeQuery();

            while (result.next()) {
                Rate r = new Rate(result.getInt(1),
                        result.getInt(2),
                        result.getInt(3));

                rateList.add(r);
            }
            return rateList;
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
