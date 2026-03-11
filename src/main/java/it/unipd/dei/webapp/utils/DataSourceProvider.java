package it.unipd.dei.webapp.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider {
    private static DataSource ds = null;

    public static synchronized DataSource getDataSource() throws NamingException {
        if (ds == null) {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ocs");
        }
        return ds;
    }
}