package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class UpdateProductDAO {
    private final Product product;
    private static final String STATEMENT_CARPET_UPDATE = "UPDATE carpet.product SET product_name=?, picture=?, quantity=?, no_color=?, dimension=?, material=?, price=?, category=?, quality=?  WHERE product_id=?";
    private final Connection con;

    public UpdateProductDAO(Product product, Connection con) {
        this.product = product;
        this.con = con;
    }

    public int updateProduct() throws SQLException{
        PreparedStatement stmnt = null;
        try{
            stmnt = con.prepareStatement(STATEMENT_CARPET_UPDATE);
            stmnt.setString(1, this.product.getProduct_name());
            stmnt.setString(2, this.product.getPicture());
            stmnt.setInt(3, this.product.getQuantity());
            stmnt.setInt(4, this.product.getNo_color());
            stmnt.setString(5, this.product.getDimension());
            stmnt.setObject(6, this.product.getMaterial(), Types.OTHER);
            stmnt.setInt(7, this.product.getPrice());
            stmnt.setObject(8, this.product.getCategory(), Types.OTHER);
            stmnt.setObject(9, this.product.getQuality(), Types.OTHER);
            stmnt.setInt(10, this.product.getProduct_id());

            int resultUpdate = stmnt.executeUpdate();
            if (resultUpdate == 1){
                return 0;
        }
            else {
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
