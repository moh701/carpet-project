package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.resource.Object;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetObjectDAO {
    private static final String STATEMENT_OBJECT_GET = "SELECT * FROM carpet.object WHERE product_id=(SELECT product_id FROM carpet.product WHERE product_name = ?)";
    private final Connection con;
    private final Product product;

    public GetObjectDAO(Connection con, Product prd) {
        this.con = con;
        this.product = prd;
    }

    public Object getobject() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Object obj=null;

        try {
            stmnt = con.prepareStatement(STATEMENT_OBJECT_GET);
            stmnt.setString(1, product.getProduct_name());
            result = stmnt.executeQuery();

            if (result.next()) {
                obj = new Object(result.getInt(1), result.getString(2));
            }
            return obj;
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
