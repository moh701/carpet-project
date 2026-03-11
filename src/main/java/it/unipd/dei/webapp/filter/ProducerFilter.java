package it.unipd.dei.webapp.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;


public class ProducerFilter extends AbstractFilter{
    final static Logger logger = LogManager.getLogger(ProducerFilter.class);
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/jsp/producers/login.jsp";
        String unauthorizedURI = req.getContextPath() + "/jsp/unauthorized.jsp";
        boolean loggedIn = session != null && session.getAttribute("producer") != null && session.getAttribute("role") == "producer";
        if (loggedIn) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(loginURI);
        }
    }
}
