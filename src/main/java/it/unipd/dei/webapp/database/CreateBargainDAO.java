package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.BargainHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class CreateBargainDAO {
    private static final String STATEMENT_BARGAIN_CREATION = "INSERT INTO carpet.bargain_history (customer_id, product_id, price, datetime, bargain_result) VALUES (?, ?, ?, ?, ?) ";
    private final Connection con;
    private final BargainHistory bargain_history;

    public CreateBargainDAO(Connection con, BargainHistory bargain_history) {
        this.con = con;
        this.bargain_history = bargain_history;
    }

    public int createBargain() throws SQLException {
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_BARGAIN_CREATION);
            stmnt.setInt(1, bargain_history.getCustomer_id());
            stmnt.setInt(2, bargain_history.getProduct_id());
            stmnt.setDouble(3, bargain_history.getPrice());
            stmnt.setTimestamp(4, bargain_history.getDatetime());
            stmnt.setInt(5, bargain_history.getBargain_result());

            int rs = stmnt.executeUpdate();
            if(rs == 1){
                return 1;
            } else{
                return 0;
            }
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
