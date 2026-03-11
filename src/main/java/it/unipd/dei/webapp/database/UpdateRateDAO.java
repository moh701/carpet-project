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

public class UpdateRateDAO {
    private static final String STATEMENT_RATE_UPDATE = "UPDATE carpet.rate SET product_id=?, customer_id=?, grade=? WHERE product_id=?";

    private final Connection con;
    private final Product product;
    private final Rate r;
    //private final String product_name;

    public UpdateRateDAO(Connection con, Product product, Rate rate) {
        this.con = con;
        this.r = rate;
        this.product = product;
    }

    public int updateRate() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        try {
            Rate rt = new GetRateDAO(con,product.getProduct_name()).getRate();
            if(rt==null)//(getRate(product_name)==null)
            {
                //return -2; //This product is not exist
                //insert rate
                int result = new InsertRateDAO(con,r).insertRate();
                return  result;
            }

            stmnt = con.prepareStatement(STATEMENT_RATE_UPDATE);
            stmnt.setInt(1, r.getproduct_id());
            stmnt.setInt(2, r.getCustomer_id());
            stmnt.setInt(3, r.getgrade());
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
