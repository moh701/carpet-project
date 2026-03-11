package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.BargainHistory;
import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Going to be changed after other DAO classess added
/*
Original function:
public static List<BargainHistory> searchBargain(String datetime, String email_address, String product_name, int price) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<BargainHistory> brgList = new ArrayList<BargainHistory>();

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "SELECT * FROM FROM carpet.bargain_history WHERE datetime>=TO_DATE(?, 'YYYY/MM/DD')";
            stmnt.setString(1, datetime);

            Customer c= CustomerDAO.getCustomer(email_address);
            int customer_id = c.getCustomer_id();
            if (customer_id>0) {
                query += " AND customer_id=?";
                stmnt.setInt(2, customer_id);
            }
            Product p= CarpetDAO.getProduct(product_name);
            int product_id = p.getProduct_id();
            if (product_id>0) {
                query += " AND product_id=?";
                stmnt.setInt(2, product_id);
            }
            if(price!= 0){
                query += " AND price=?";
                stmnt.setDouble(3, price);
            }

            result = stmnt.executeQuery();

            while (result.next()) {
                BargainHistory brg = new BargainHistory(result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getInt(4),
                        result.getTimestamp(5),
                        result.getInt(6));

                brgList.add(brg);
            }
            return brgList;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }
 */
public class SearchBargainDAO {
    private static final String STATEMENT_BARGAIN_SEARCH = "SELECT * FROM FROM carpet.bargain_history WHERE datetime>=TO_DATE(?, 'YYYY/MM/DD') AND customer_id=? AND product_id=?  AND price=?";
    private final Connection con;
    private final Customer customer;
    private final Product product;
    private final String datetime;
    private final double price;

    public SearchBargainDAO(Connection con, Customer customer, Product product, String datetime, double price) {
        this.con = con;
        this.customer = customer;
        this.product = product;
        this.datetime = datetime;
        this.price = price;
    }

    public ArrayList<BargainHistory> searchBargain() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        ArrayList<BargainHistory> bargain_list = new ArrayList<BargainHistory>();

        try {
            stmnt = con.prepareStatement(STATEMENT_BARGAIN_SEARCH);
            stmnt.setString(1, this.datetime);
            stmnt.setInt(2, this.customer.getCustomer_id());
            stmnt.setInt(3, this.product.getProduct_id());
            stmnt.setDouble(4, this.price);
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
