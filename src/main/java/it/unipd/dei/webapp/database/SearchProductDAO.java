package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.BargainHistory;
import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchProductDAO {
    private static final String STATEMENT_PRODUCT_SEARCH = "SELECT * FROM FROM carpet.product WHERE product_name=? AND quantity=?  AND no_color=?  AND dimension=?  AND material=? AND price=? AND category=? AND quality=?";
    private final Connection con;
    private final String product_name;
    private final int quantity;
    private final int no_color;
    private final String material;
    private String  dimension;
    private int price;
    private String category;
    private String quality;

    public SearchProductDAO(Connection con, String product_name, int quantity, int no_color, String material, String dimension, int price, String category, String quality) {
        this.con = con;
        this.product_name = product_name;
        this.quantity = quantity;
        this.no_color = no_color;
        this.material = material;
        this.dimension = dimension;
        this.price = price;
        this.category = category;
        this.quality = quality;
    }

    public ArrayList<Product> searchProduct() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        ArrayList<Product> product_list = new ArrayList<Product>();

        try {
            stmnt = con.prepareStatement(STATEMENT_PRODUCT_SEARCH);
            stmnt.setString(1, product_name);
            stmnt.setInt(2, quantity);
            stmnt.setInt(3, no_color);
            stmnt.setString(4, dimension);
            stmnt.setString(5, material);
            stmnt.setInt(6, price);
            stmnt.setString(7, category);
            stmnt.setString(8, quality);
            result = stmnt.executeQuery();

            while (result.next()) {
                Product carpet = null;

                carpet = new Product(result.getInt(1),
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

                product_list.add(carpet);
            }
            return product_list;
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
