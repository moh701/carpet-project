package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.GetProductByIDDAO;
import it.unipd.dei.webapp.database.GetProductByProducerIDDAO;
import it.unipd.dei.webapp.database.SearchProductDAO;
import it.unipd.dei.webapp.resource.CartItem;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet(name = "CarpetViewServlet", urlPatterns = "/product-view/*")
public class CarpetViewServlet extends AbstractServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String op = req.getRequestURI();
        // URL : ***.com/product-view/product_id
        // URL : ***.com/product-view/all/producer_id
        // URL : ***.com/product-view/search
        // URL : ***.com/product-view/filter
        op = op.substring(op.lastIndexOf("product-view") + 13);

        try {
            switch (op) {
                case "view":
                    doViewProduct(req, res);
                    //req.getRequestDispatcher("/jsp/product.jsp").forward(req, res);
                    break;
                case "all/":
                    doViewAllProductsOfProducer(req, res);
                    break;
                case "search/":
                    doSearch(req, res);
                    break;
                default:
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    res.getWriter().write("Bad Request!");
            }
        } catch (IOException e) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            writeError(res, ec);
            logger.error("stacktrace:", e);
        }
    }

    private void doViewProduct(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String product_idString = req.getParameter("product_id");
        System.out.println(product_idString);
        if (product_idString == null || product_idString.equals("") || Integer.parseInt(product_idString) < 0) {
            //res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //res.getWriter().write("Bad Request!");
            ErrorCode ec = ErrorCode.PRODUCT_NOT_EXISTED;
            writeError(res, ec);
            logger.error("stacktrace:", ec);
        } else {
            try {
                int product_id = Integer.parseInt(product_idString);
                Product p = new GetProductByIDDAO(product_id, getDataSource().getConnection()).getProductByID();
                //res.setStatus(200);
                //p.toJSON(res.getOutputStream());
                req.setAttribute("product", p);
                req.getRequestDispatcher("/jsp/product.jsp").forward(req, res);
            } catch (NumberFormatException e) {
                writeError(res, ErrorCode.WRONG_FORMAT);
            } catch (SQLException e) {
                writeError(res, ErrorCode.INTERNAL_ERROR);
                logger.error("stacktrace", e);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void doViewAllProductsOfProducer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String producer_idString = req.getParameter("producer_id");
        try {
            if (producer_idString == null || producer_idString.equals("")) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                writeError(res, ec);
                logger.error("stacktrace:", ec);
                //res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                //res.getWriter().write("Bad Request!");
            } else {
                List<Product> allProducts = null;
                int producer_id = Integer.parseInt(producer_idString);
                allProducts = new GetProductByProducerIDDAO(producer_id, getDataSource().getConnection()).getProductByProducerID();
                if (allProducts == null) {
                    ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                    writeError(res, ec);
                    logger.error("stacktrace:", ec);
                } else {
                    //res.setStatus(200);
                    //JSONObject jsnObj = new JSONObject();
                    //JSONObject carpets = new JSONObject();
                    //jsnObj.put("size", allProducts.size());
                    //for (int i = 0; i < allProducts.size(); i++) {
                    //    carpets.put("Carpet" + (i + 1), allProducts.get(i).toJSON());
                    //}
                    //jsnObj.put("Products", carpets);
                    //res.setStatus(HttpServletResponse.SC_OK);
                    //res.setContentType("application/json");
                    //res.getWriter().write(jsnObj.toString());
                    req.setAttribute("message", "Products retrieved successfully.");
                    req.setAttribute("allProducts", allProducts);
                    req.getRequestDispatcher("/jsp/productlist.jsp").forward(req, res);

                }
            }
        } catch (SQLException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace", e);
        } catch (ServletException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace", e);
        }
    }

    private void doSearch(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String product_name = req.getParameter("product_name");
        String quantityString = req.getParameter("quantity");
        String no_colorString = req.getParameter("no_color");
        String dimension = req.getParameter("dimension");
        String material = req.getParameter("material");
        String priceString = req.getParameter("price");
        String category = req.getParameter("category");
        String quality = req.getParameter("quality");

        int quantity;
        int no_color;
        Integer price;
        if (quantityString == null || quantityString.equals("")) {
            quantity = -1;
        } else {
            quantity = Integer.parseInt(quantityString);
        }

        if (no_colorString == null || no_colorString.equals("")) {
            no_color = -1;
        } else {
            no_color = Integer.parseInt(no_colorString);
        }

        if (priceString == null || priceString.equals("")) {
            price = -1;
        } else {
            price = Integer.parseInt(priceString);
        }

        List<Product> products = null;
        try {
            products = new SearchProductDAO(getDataSource().getConnection(), product_name, quantity, no_color, material,dimension, price, category, quality ).searchProduct();

            if (products == null) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                writeError(res, ec);
                logger.error("stacktrace:", ec);
            } else {
                //res.setStatus(200);
                //JSONObject jsnObj = new JSONObject();
                //JSONObject carpets = new JSONObject();
                //jsnObj.put("size", products.size());
                //for (int i = 0; i < products.size(); i++) {
                //    carpets.put("Carpet" + (i + 1), products.get(i).toJSON());
                //}
                //carpets.put("Products", carpets);
                //res.setStatus(200);
                //res.setStatus(HttpServletResponse.SC_OK);
                //res.setContentType("application/json");
                //res.getWriter().write(jsnObj.toString());

                req.setAttribute("message", "Products searched successfully.");
                req.setAttribute("products", products);
                req.getRequestDispatcher("/jsp/productlist.jsp").forward(req, res);


            }
            String product_idString = req.getParameter("product_id");
            if (product_idString == null || product_idString.equals("") || Integer.parseInt(product_idString) < 0) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write("Bad Request!");
            } else {

                int product_id = Integer.parseInt(product_idString);
                Product p = new GetProductByIDDAO(product_id, getDataSource().getConnection()).getProductByID();
                res.setStatus(200);
                p.toJSON(res.getOutputStream());
            }
        } catch (NumberFormatException e) {
            writeError(res, ErrorCode.WRONG_FORMAT);
        } catch (SQLException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace", e);
        } catch (ServletException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // URL : ***.com/product/view/addtocart
        String op = req.getRequestURI();
        String error = "";
        //Parse the request
        op = op.substring(op.lastIndexOf("product-view") + 13);

        if (op.equals("addtocart/")){
            CartItem cart;
            if (req.getSession().getAttribute("cart") == null){
                cart = new CartItem();
            } else{
                cart = (CartItem) req.getSession().getAttribute("cart");
            }

            try{
                if (req.getParameter("product_id") == null){
                    writeError(res, ErrorCode.INTERNAL_ERROR);
                } else {
                    int product_id = Integer.parseInt(req.getParameter("product_id"));
                    Product product = new GetProductByIDDAO(product_id, getDataSource().getConnection()).getProductByID();
                    if (cart.getItems().containsKey(product)) {
                        cart.updateQuantity(product, cart.getItems().get(product) + Integer.parseInt(req.getParameter("quantity")));
                    } else {
                        cart.addItem(product, Integer.parseInt(req.getParameter("quantity")));
                    }
                    req.setAttribute("message", "Item added to cart successfully.");
                    req.getSession().setAttribute("cart", cart);
                    req.setAttribute("product_id",product_id);
                    res.sendRedirect(req.getContextPath() + "/product-view/view?product_id="+ product_id);
                }
            } catch (SQLException e){
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
            }

        } else{
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }

    }
}