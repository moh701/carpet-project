package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.BargainHistory;
import it.unipd.dei.webapp.resource.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetBargainHistoryDAO {
    private static final String STATEMENT_BARGAIN_GET = "SELECT * FROM FROM carpet.bargain_history WHERE product_id=? AND customer_id=?";
    private final Connection con;
    private final int customer_id;
    private final int product_id;

    public GetBargainHistoryDAO(Connection con, int customer_id, int product_id) {
        this.con = con;
        this.customer_id = customer_id;
        this.product_id = product_id;
    }

    public ArrayList<BargainHistory> getBargainHistory() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        ArrayList<BargainHistory> bargain_list = new ArrayList<BargainHistory>();

        try {
            stmnt = con.prepareStatement(STATEMENT_BARGAIN_GET);
            stmnt.setInt(1,product_id);
            stmnt.setInt(2,customer_id);
            result = stmnt.executeQuery();
            while (result.next()) {
                BargainHistory bargain = new BargainHistory(result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getInt(4),
                        result.getTimestamp(5),
                        result.getInt(6));
                bargain_list.add(bargain);
            }
            return bargain_list;
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
