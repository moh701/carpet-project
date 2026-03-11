package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.GetAllRateDAO;
import it.unipd.dei.webapp.database.InsertRateDAO;
import it.unipd.dei.webapp.resource.Rate;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RateServlet extends AbstractServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // URL : ***.com/product/rate/get
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);
        if (op.contains("rate")) {
            op = op.substring(op.lastIndexOf("rate") + 5);
            if (op.equals("get")) {
                String product_idString = req.getParameter("product_id");
                if (product_idString == null || product_idString.equals("") || Integer.parseInt(product_idString) < 0) {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    res.getWriter().write("Bad Request!");
                } else {
                    try {
                        int product_id = Integer.parseInt(product_idString);

                        List<Rate> rates = new GetAllRateDAO(getDataSource().getConnection(), product_id).getAllRateByProductId(); //RateDAO.getAllRateByProductId(product_id); //shili edited
                        int sum = 0;
                        for (Rate rate : rates) {
                            sum += rate.getgrade();
                        }
                        float mean_rate = (float) sum / rates.size();
                        req.setAttribute("mean_rate", mean_rate);
                    } catch (NumberFormatException e) {
                        writeError(res, ErrorCode.WRONG_FORMAT);
                    } catch (SQLException | NamingException e) {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("stacktrace", e);
                    }
                }
            } else {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                logger.error("Bad Request!");
            }
        } else {
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
            logger.error("Bad Request!");
        }
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // URL : ***.com/product/rate/post
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);
        if (op.contains("rate")) {
            op = op.substring(op.lastIndexOf("rate") + 5);
            if (op.equals("post")) {
                String product_idString = req.getParameter("product_id");
                String customer_idString = req.getParameter("customer_id");
                String gradeString = req.getParameter("grade");
                if (product_idString == null || product_idString.equals("")
                        || customer_idString == null || customer_idString.equals("")
                        || gradeString == null || gradeString.equals("")) {
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    req.setAttribute("message", ec.getErrorMessage());
                    //res.setStatus(ec.getHTTPCode());
                    //req.getRequestDispatcher("/jsp/edit-product.jsp").forward(req, res);
                } else {
                    int product_id = Integer.parseInt(product_idString);
                    int customer_id = Integer.parseInt(customer_idString);
                    int grade = Integer.parseInt(gradeString);

                    Rate r = new Rate(product_id, customer_id, grade);
                    int insertResult = 0;
                    try {
                        insertResult = new InsertRateDAO(getDataSource().getConnection(), r).insertRate(); //RateDAO.insertRate(r); //shili edited
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (NamingException e) {
                        throw new RuntimeException(e);
                    }

                    if (insertResult == 0) {
                        req.setAttribute("message", "Rate added successfully.");
                        //req.getRequestDispatcher("/jsp/message-page.jsp").forward(req, res);
                    }  else {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("problem when updating model: " + req.getRequestURL());
                    }
                }
            } else {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                logger.error("Bad Request!");
            }
        } else {
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
            logger.error("Bad Request!");
        }
    }
}
