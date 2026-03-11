package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterCustomerDAO {
    private final Customer customer;
    private final Connection con;
    private static final String STATEMENT_CUSTOMER_REGISTER = "INSERT INTO carpet.customer (name, surname, passwd, email_address, mobile_number) VALUES (?, ?, ?, ?, ?);";
    private static final String STATEMENT_Check_Email = "SELECT COUNT(*) FROM carpet.customer WHERE email_address = ?;";

    public RegisterCustomerDAO(Customer customer, Connection con) {
        this.customer = customer;
        this.con = con;
    }

    public int registerCustomer() throws SQLException{
        PreparedStatement stmnt = null;
        try {
            if (isEmailRegistered(customer.getEmail_address())) {
                return -1;
            }
            stmnt = con.prepareStatement(STATEMENT_CUSTOMER_REGISTER);
            stmnt.setString(1, customer.getName());
            stmnt.setString(2, customer.getSurname());
            stmnt.setString(3, hashPassword(customer.getPassword()));
            stmnt.setString(4, customer.getEmail_address());
            stmnt.setString(5, customer.getMobile_number());
            int result = stmnt.executeUpdate();

            if (result == 1) {
                return 0;
            } else {
                return -1;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }

    private boolean isEmailRegistered(String email) throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet rs = null;

        try {
            stmnt = con.prepareStatement(STATEMENT_Check_Email);
            stmnt.setString(1, email);
            rs = stmnt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
