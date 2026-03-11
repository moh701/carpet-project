package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.resource.Object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertObjectDAO {
    private static final String STATEMENT_OBJECT_INSERT = "INSERT INTO carpet.object(product_id) VALUES";
    private final Connection con;
    private final int quantity;
    private final int product_id;

    public InsertObjectDAO(Connection con, int quantity, int product_id) {
        this.con = con;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public int insertObject()  throws SQLException {
        PreparedStatement stmnt = null;
        String restQuery="";

        try {
            for (int i=0; i<quantity; i++)
            {
                if(i<quantity -2)
                {
                    restQuery +="(?),";
                }
                else {
                    restQuery +="(?);";
                }
            }
            String query=  STATEMENT_OBJECT_INSERT + restQuery;

            stmnt = con.prepareStatement(query);
            stmnt.setInt(1, product_id);
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
