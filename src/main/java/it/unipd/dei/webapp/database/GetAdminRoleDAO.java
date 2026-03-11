package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAdminRoleDAO {
    private final String email;
    private final Connection con;
    private static final String STATEMENT_adminRole_GET = "SELECT role FROM carpet.admin WHERE email_address=?;";

    public GetAdminRoleDAO(String email, Connection con) {
        this.email = email;
        this.con = con;
    }

    public String getAdminRole() throws SQLException {
        PreparedStatement stmnt = null;
        String role = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_adminRole_GET);
            stmnt.setString(1, email);
            role = String.valueOf(stmnt.executeQuery());
            return role;

        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
