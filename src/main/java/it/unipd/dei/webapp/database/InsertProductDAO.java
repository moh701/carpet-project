package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.BargainHistory;
import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class InsertProductDAO {
    private static final String STATEMENT_PRODUCT_CREATION = "INSERT INTO carpet.product(producer_id, product_name, picture, quantity, no_color, dimension, material, price, category, quality) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final Connection con;
    private final Product product;

    public InsertProductDAO(Connection con, Product product) {
        this.con = con;
        this.product = product;
    }

    public int insertProduct() throws SQLException {
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_PRODUCT_CREATION);
            stmnt.setInt(1, this.product.getProducer_id());
            stmnt.setString(2, this.product.getProduct_name());
            stmnt.setString(3, this.product.getPicture());
            stmnt.setInt(4, this.product.getQuantity());
            stmnt.setInt(5, this.product.getNo_color());
            stmnt.setString(6, this.product.getDimension());
            stmnt.setObject(7, this.product.getMaterial(), Types.OTHER);
            stmnt.setInt(8, this.product.getPrice());
            stmnt.setObject(9, this.product.getCategory(), Types.OTHER);
            stmnt.setObject(10, this.product.getQuality(), Types.OTHER);

            int rs = stmnt.executeUpdate();
            if(rs == 1){
                return 0;
            } else{
                return 1;
            }
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
