package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticateCustomerDAO {
    private static final String STATEMENT_CUSTOMER_AUTHENTICATE = "SELECT * FROM carpet.customer WHERE email_address=? AND passwd=?;";
    private final Customer customer;
    private final Connection con;

    public AuthenticateCustomerDAO(Customer customer, Connection con) {
        this.customer = customer;
        this.con = con;
    }

    public int authenticateCustomer() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_AUTHENTICATE);
            stmnt.setString(1, customer.getEmail_address());
            String hashedPassword = hashPassword(customer.getPassword());
            stmnt.setString(2, hashedPassword);
            result = stmnt.executeQuery();
            int authenticationResult = -1;

            if (result.next()) {
                String storedPassword = result.getString("passwd");

                if (hashedPassword.equals(storedPassword)) {
                    authenticationResult = 0;
                }
            }
            return authenticationResult;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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


    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());

        // Convert the hashed bytes to a hexadecimal string
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
