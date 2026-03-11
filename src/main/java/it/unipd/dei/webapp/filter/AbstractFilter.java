package it.unipd.dei.webapp.filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractFilter extends HttpFilter {
    Logger logger;
    @Override
    public void init(FilterConfig config) throws ServletException {
        logger = LogManager.getLogger(this.getClass());
    }
    @Override
    public void destroy() {}
}