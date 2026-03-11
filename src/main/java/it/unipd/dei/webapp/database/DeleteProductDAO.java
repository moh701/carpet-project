package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteProductDAO {
    private final int product_id;
    private static final String STATEMENT_DELETE_CARPET = "DELETE FROM carpet.product WHERE product_id=?";
    private final Connection con;

    public DeleteProductDAO(int product_id, Connection con) {
        this.product_id = product_id;
        this.con = con;
    }

    public int deleteProduct() throws SQLException{
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_DELETE_CARPET);
            stmnt.setInt(1, product_id);
            int resultDeletion = stmnt.executeUpdate();
            if (resultDeletion == 1){
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
