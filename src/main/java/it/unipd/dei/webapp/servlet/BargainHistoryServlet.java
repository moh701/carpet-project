package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.CreateBargainDAO;
import it.unipd.dei.webapp.database.GetBargainHistoryDAO;
import it.unipd.dei.webapp.resource.BargainHistory;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BargainHistoryServlet extends AbstractServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // URL : ***.com/product/bargain/get
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);

        if (op.contains("bargain")) {
            op = op.substring(op.lastIndexOf("bargain") + 8);
            if (op.equals("get")) {
                String product_idString = req.getParameter("product_id");
                String customer_idString = req.getParameter("customer_id");

                if (product_idString == null || product_idString.equals("") || Integer.parseInt(product_idString) < 0
                        || customer_idString == null || customer_idString.equals("") || Integer.parseInt(customer_idString) < 0) {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    res.getWriter().write("Bad Request!");
                } else {
                    try {
                        int product_id = Integer.parseInt(product_idString);
                        int customer_id = Integer.parseInt(customer_idString);

                        List<BargainHistory> bargainHistoryList = new GetBargainHistoryDAO(getDataSource().getConnection(),product_id, customer_id).getBargainHistory();

                        req.setAttribute("BargainHistory", bargainHistoryList);
                        req.setAttribute("message", "bargainHistory retrieved successfully.");
                        req.getRequestDispatcher("/jsp/chat.jsp").forward(req, res);

//                        JSONObject jsnObj = new JSONObject();
//                        JSONObject bargains = new JSONObject();
//                        jsnObj.put("size", bargainHistoryList.size());
//                        for (int i = 0; i < bargainHistoryList.size(); i++) {
//                            bargains.put("Bargain" + (i + 1), bargainHistoryList.get(i).toJSON());
//                        }
//                        jsnObj.put("BargainHistory", bargains);
                    } catch (NumberFormatException e) {
                        writeError(res, ErrorCode.WRONG_FORMAT);
                        logger.error("stacktrace", e);
                    } catch (SQLException | ServletException e) {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("stacktrace", e);
                    }
                }
            } else {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                logger.error("Operation Unknown!");
            }
        } else {
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
            logger.error("Operation Unknown!");
        }
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // URL : ***.com/product/bargain/post
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);
        if (op.contains("bargain")) {
            op = op.substring(op.lastIndexOf("bargain") + 5);
            if (op.equals("post")) {
                String customer_idString = req.getParameter("customer_id");
                String product_idString = req.getParameter("product_id");
                String priceString = req.getParameter("priceString");
                String dateTimeString = req.getParameter("dateTimeString");

                if (product_idString == null || product_idString.equals("")
                        || customer_idString == null || customer_idString.equals("")
                        || priceString == null || priceString.equals("")
                        || dateTimeString == null || dateTimeString.equals("")) {
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    req.setAttribute("message", ec.getErrorMessage());
                    res.setStatus(ec.getHTTPCode());
                    req.getRequestDispatcher("/jsp/edit-product.jsp").forward(req, res);
                } else {
                    int customer_id = Integer.parseInt(customer_idString);
                    int product_id = Integer.parseInt(product_idString);
                    int price = Integer.parseInt(priceString);
                    Timestamp dateTime = new Timestamp(Long.parseLong(dateTimeString));

                    BargainHistory bh = new BargainHistory(customer_id, product_id, price, dateTime);
                    int insertResult = 0;
                    try {
                        insertResult = new CreateBargainDAO(getDataSource().getConnection(), bh).createBargain();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    if (insertResult == 0) {
                        req.setAttribute("message", "Offer registered.");
                    }  else {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("problem when updating model: " + req.getRequestURL());
                    }
                }
            } else {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                logger.error("Operation Unknown!");
            }
        } else {
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
            logger.error("Operation Unknown!");
        }
    }
}
