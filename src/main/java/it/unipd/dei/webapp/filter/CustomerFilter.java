package it.unipd.dei.webapp.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
public class CustomerFilter extends AbstractFilter{
    final static Logger logger = LogManager.getLogger(CustomerFilter.class);
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/jsp/customers/login.jsp";
        boolean loggedIn = session != null && session.getAttribute("customer") != null && session.getAttribute("role") == "customer";
        if (loggedIn) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(loginURI);
        }

    }
}
