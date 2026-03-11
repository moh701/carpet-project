package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.resource.Product;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllProductDAO {
    private static final String STATEMENT_AllProduct_GET ="SELECT * FROM carpet.product";
    private final Connection con;

    public GetAllProductDAO(Connection con) {
        this.con = con;
    }

    public List<Product> GetAllProduct() throws SQLException, NamingException {
        List<Product> product = new ArrayList<>();
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_AllProduct_GET);
            result = stmnt.executeQuery();
            while (result.next()) {
                Product pr = new Product(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getInt(5),
                        result.getInt(6),
                        result.getString(7),
                        result.getString(8),
                        result.getInt(9),
                        result.getString(10),
                        result.getString(11)
                );
                product.add(pr);
            }
            return product;

        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            if (result != null) {
                result.close();
            }
            con.close();
        }
    }

}
