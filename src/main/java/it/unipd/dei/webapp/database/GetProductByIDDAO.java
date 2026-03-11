package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProductByIDDAO {
    private final int id;
    private static final String STATEMENT_CARPET_GET_BY_ID = "SELECT * FROM carpet.product WHERE product_id =  ?";
    private final Connection con;
    public GetProductByIDDAO(int id, Connection con) {
        this.id = id;
        this.con = con;
    }

    public Product getProductByID() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Product product = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CARPET_GET_BY_ID);
            stmnt.setInt(1, this.id);
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
