package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetProductByProducerIDDAO {
    private final int producer_id;
    private static final String STATEMENT_CARPET_BY_PRODUCER_ID = "SELECT * FROM carpet.product WHERE producer_id=?";
    private final Connection con;
    public GetProductByProducerIDDAO(int producer_id, Connection con) {
        this.producer_id = producer_id;
        this.con = con;
    }
    public List<Product> getProductByProducerID() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<Product> productList = new ArrayList<Product>();

        try {
            stmnt = con.prepareStatement(STATEMENT_CARPET_BY_PRODUCER_ID);
            stmnt.setInt(1, this.producer_id);
            result = stmnt.executeQuery();
            while (result.next()) {
                Product product = new Product(result.getInt(1),
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
                productList.add(product);
            }
            return productList;
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
