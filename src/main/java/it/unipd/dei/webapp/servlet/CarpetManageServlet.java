package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.DeleteProductDAO;
import it.unipd.dei.webapp.database.InsertProductDAO;
import it.unipd.dei.webapp.database.UpdateProductDAO;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class CarpetManageServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // URL : ***.com/product/add
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);

        if (op.equals("add")) {
            int producer_id = -1;
            String product_name = "";
            String picture = "";
            int quantity = -1;
            int no_color = -1;
            String dimension = "";
            String material = "";
            Integer price = -1;
            String category = "";
            String quality = "";

            try {

                producer_id = Integer.parseInt(req.getParameter("producer_id"));
                product_name = req.getParameter("product_name");
                picture = req.getParameter("picture");
                quantity = Integer.parseInt(req.getParameter("quantity"));
                no_color = Integer.parseInt(req.getParameter("no_color"));
                dimension = req.getParameter("dimension");
                material = req.getParameter("material");
                price = Integer.parseInt(req.getParameter("price"));
                category = req.getParameter("category");
                quality = req.getParameter("quality");

                if (product_name.isEmpty() || quantity < 1 || producer_id < 0 || price < 0) {
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    req.setAttribute("message", ec.getErrorMessage());
                    req.getRequestDispatcher("/jsp/edit-product.jsp").forward(req, res);
                } else {
                    Product p = new Product(producer_id, product_name, picture, quantity,
                            no_color, dimension, material, price, category, quality);
                    int insertResult = new InsertProductDAO(getDataSource().getConnection(), p).insertProduct();
                    if (insertResult == 0) {
                        req.setAttribute("message", "Product inserted correctly");
                        req.getRequestDispatcher("/jsp/producers/dashboard.jsp").forward(req, res);
                    } else {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("unknown error: " + req.getRequestURL());
                    }
                }

            } catch (SQLException e) {
                logger.error("stacktrace:", e);
                writeError(res, ErrorCode.INTERNAL_ERROR);
            }
        }
    }


    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // URL : ***.com/product/update
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);

        if (op.equals("update")) {
            int producer_id = -1;
            String product_name = "";
            String picture = "";
            int quantity = -1;
            int no_color = -1;
            String dimension = "";
            String material = "";
            Integer price = -1;
            String category = "";
            String quality = "";

            try {
                producer_id = Integer.parseInt(req.getParameter("producer_id"));
                product_name = req.getParameter("product_name");
                picture = req.getParameter("picture");
                quantity = Integer.parseInt(req.getParameter("quantity"));
                no_color = Integer.parseInt(req.getParameter("no_color"));
                dimension = req.getParameter("dimension");
                material = req.getParameter("material");
                price = Integer.parseInt(req.getParameter("price"));
                category = req.getParameter("category");
                quality = req.getParameter("quality");

                if (product_name.isEmpty() || quantity < 1 || producer_id < 0 || price < 0) {
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    req.setAttribute("message", ec.getErrorMessage());
                    req.getRequestDispatcher("/jsp/edit-product.jsp").forward(req, res);
                } else {
                    Product p = new Product(producer_id, product_name, picture, quantity,
                            no_color, dimension, material, price, category, quality);

                    int updateResult = new UpdateProductDAO(p, getDataSource().getConnection()).updateProduct();
                    System.out.println(updateResult);
                    if (updateResult == 0) {
                        req.setAttribute("message", "Product updated correctly");
                        req.getRequestDispatcher("/jsp/producers/dashboard.jsp").forward(req, res);
                    }
                    if (updateResult == -2) {
                        ErrorCode ec = ErrorCode.PRODUCT_NOT_EXISTED;
                        req.setAttribute("message", ec.getErrorMessage());
                        req.setAttribute("product", p);
                        req.getRequestDispatcher("/jsp/producers/dashboard.jsp").forward(req, res);
                    } else {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("problem when updating model: " + req.getRequestURL());
                    }
                }
            } catch (SQLException e) {
                logger.error("stacktrace:", e);
                writeError(res, ErrorCode.INTERNAL_ERROR);
            }
        }
    }


    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // URL : ***.com/product/delete
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product") + 8);

        if (op.equals("delete")) {
            try {
                int product_id = Integer.parseInt(req.getParameter("producer_id"));

                if (product_id < 0) {
                    writeError(res, ErrorCode.INTERNAL_ERROR);
                    logger.error("Error Accessing the Product: " + req.getRequestURL());
                } else {
                    int deleteResult = new DeleteProductDAO(product_id, getDataSource().getConnection()).deleteProduct();
                    if (deleteResult == 0) {
                        req.setAttribute("message", "Product deleted correctly");
                        req.getRequestDispatcher("/jsp/producers/dashboard.jsp").forward(req, res);
                    } else {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                        logger.error("unknown error: " + req.getRequestURL());
                    }
                }
            } catch (SQLException e) {
                logger.error("stacktrace:", e);
                writeError(res, ErrorCode.INTERNAL_ERROR);
            }
        }
    }
}
