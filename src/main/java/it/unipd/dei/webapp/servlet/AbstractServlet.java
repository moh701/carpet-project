package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {

    Logger logger;


    /**
     * The connection pool to the database.
     */
    private DataSource ds;


    //override the init method: here you should put the initialization of your servlet
    @Override
    public void init(ServletConfig config) throws ServletException {

        // the JNDI lookup context
        InitialContext cxt;

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/ocs");
        } catch (NamingException e) {
            ds = null;

            throw new ServletException(
                    String.format("Impossible to access the connection pool to the database: %s",
                            e.getMessage()));
        }
    }


    //override destroy method: here you should put the behaviour of your servlet when destroyed
    @Override
    public void destroy(){
        super.destroy();
    }

    public void writeError(HttpServletResponse res, ErrorCode ec) throws IOException {
        res.setStatus(ec.getHTTPCode());
        res.getWriter().write(ec.toJSON().toString());
    }



    /**
     * Returns the {@code DataSource} for managing the connection pool to the database.
     *
     * @return the {@code DataSource} for managing the connection pool to the database
     */
    protected final DataSource getDataSource() {
        return ds;
    }

}