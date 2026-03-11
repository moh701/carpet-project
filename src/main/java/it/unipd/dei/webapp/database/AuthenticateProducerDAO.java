package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Producer;

import javax.naming.NamingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticateProducerDAO {
    private static final String STATEMENT_Authenticate_Producer = "SELECT * FROM carpet.producer WHERE email_address=? AND passwd=?;";
    private final Connection con;
    private final Producer p;

    public AuthenticateProducerDAO(Connection con, Producer prd) {
        this.con = con;
        this.p = prd;
    }


    public int authenticateProducer() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_Authenticate_Producer);
            stmnt.setString(1, p.getEmail_address());
            String hashedPassword = hashPassword(p.getPassword());
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
            if (stmnt != null)
            {
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
