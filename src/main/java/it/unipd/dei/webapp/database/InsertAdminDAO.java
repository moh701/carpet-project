package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertAdminDAO {
    private final Admin admin;
    private final Connection con;
    private static final String STATEMENT_Admin_Insert = "INSERT INTO carpet.admin (email_address, passwd, role) VALUES (?, ?, ?,);";

    public InsertAdminDAO(Admin admin, Connection con) {
        this.admin = admin;
        this.con = con;
    }

    public int registerAdmin() throws SQLException{
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_Admin_Insert);
            stmnt.setString(1, admin.getEmail_address());
            stmnt.setString(2, admin.getPassword());
            stmnt.setString(3, admin.getRole());
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
}
