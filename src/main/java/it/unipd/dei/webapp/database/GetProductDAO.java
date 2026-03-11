package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProductDAO {
    private final int product_id;
    private static final String STATEMENT_CARPET_GET = "SELECT * FROM carpet.product WHERE product_id = ?";
    private final Connection con;
    public GetProductDAO(int product_id, Connection con) {
        this.product_id = product_id;
        this.con = con;
    }

    public Product getProduct() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Product product = null;

        try {
            stmnt = con.prepareStatement(STATEMENT_CARPET_GET);
            stmnt.setInt(1, this.product_id);
            result = stmnt.executeQuery();

            if (result.next()) {
                product = new Product(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getInt(5),
                        result.getInt(6),
                        result.getString(7),
                        result.getString(8),
                        result.getInt(9),
                        result.getString(10),
                        result.getString(11));
            }
            return product;
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
