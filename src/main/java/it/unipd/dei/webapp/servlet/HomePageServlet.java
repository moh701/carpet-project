package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.GetAllProductDAO;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomePageServlet", urlPatterns = "/homepage/*")
public class HomePageServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Product> allProducts = null;
        String m = "";
        String action = req.getRequestURI();
        if (!action.contains("homepage")){
            try {
                allProducts = new GetAllProductDAO(getDataSource().getConnection()).GetAllProduct();
                if (allProducts != null && !allProducts.isEmpty()) {
                    req.setAttribute("allProducts", allProducts);
                } else {
                    m = "No products found.";
                }
            } catch (SQLException e) {
                m = "Cannot search for the resources: unexpected error while accessing the database." + e.getMessage();
            } catch (NamingException e) {
                m = "Cannot search for the resources in the db." + e.getMessage();
            }
            req.getSession().setAttribute("message", m);
            req.getSession().setAttribute("allProducts", allProducts);
            req.getRequestDispatcher("/jsp/homepage.jsp").forward(req, res);
        }
        else{
            action = action.substring(action.lastIndexOf("homepage") + 9);
            switch(action){
                case "":
                    try {
                        allProducts = new GetAllProductDAO(getDataSource().getConnection()).GetAllProduct();
                        m = "Successfully searched.";

                        // Pagination logic
                        int itemsPerPage = 9;
                        int totalItems = allProducts.size();
                        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

                        int currentPage = 1;
                        String pageParam = req.getParameter("page");
                        if (pageParam != null) {
                            currentPage = Integer.parseInt(pageParam);
                            if (currentPage < 1) {
                                currentPage = 1;
                            } else if (currentPage > totalPages) {
                                currentPage = totalPages;
                            }
                        }

                        int startIndex = (currentPage - 1) * itemsPerPage;
                        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

                        List<Product> products = allProducts.subList(startIndex, endIndex);


                        if (!allProducts.isEmpty()) {

                            req.getSession().setAttribute("allProducts", allProducts);
                            req.setAttribute("products", products);
                            req.setAttribute("totalPages", totalPages);
                            req.setAttribute("currentPage", currentPage);
                        } else {
                            m = "No products found.";
                        }
                    } catch (NamingException ex) {
                        m = "Cannot search for the resources in the db." + ex.getMessage();
                    } catch (SQLException ex) {
                        m = "Cannot search for the resources: unexpected error while accessing the database." + ex.getMessage();
                    }

                    req.getSession().setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/homepage.jsp").forward(req, res);
                    break;

                case "login/":
                    req.getSession().invalidate();
                    req.getRequestDispatcher("/jsp/startpage.jsp").forward(req, res);
                    break;

                default:
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                    writeError(res, ErrorCode.OPERATION_UNKNOWN);
                    break;
            }
        }
    }
}