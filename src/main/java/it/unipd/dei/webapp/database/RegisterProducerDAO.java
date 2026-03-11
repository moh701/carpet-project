package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;

import javax.naming.NamingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterProducerDAO {
    private static final String STATEMENT_Register_Producer = "INSERT INTO carpet.producer (name, surname, passwd, email_address, mobile_number, bank_account, brand, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String STATEMENT_Check_Email = "SELECT COUNT(*) FROM carpet.producer WHERE email_address = ?;";
    private final Connection con;
    private Producer p;

    public RegisterProducerDAO(Connection con, Producer prd) {
        this.con = con;
        this.p = prd;
    }

    public int registerProducer() throws NamingException, SQLException, NoSuchAlgorithmException {
        PreparedStatement stmnt = null;

        try {
            if (isEmailRegistered(p.getEmail_address())) {
                return -1;
            }

            stmnt = con.prepareStatement(STATEMENT_Register_Producer);
            stmnt.setString(1, p.getName());
            stmnt.setString(2, p.getSurname());
            stmnt.setString(3, hashPassword(p.getPassword()));
            stmnt.setString(4, p.getEmail_address());
            stmnt.setString(5, p.getMobile_number());
            stmnt.setString(6, p.getBank_account());
            stmnt.setString(7, p.getBrand());
            stmnt.setString(8, p.getAddress());

            int result = stmnt.executeUpdate();

            if (result == 1) {
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
