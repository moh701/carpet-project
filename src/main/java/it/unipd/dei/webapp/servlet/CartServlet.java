package it.unipd.dei.webapp.servlet;


import it.unipd.dei.webapp.database.GetCustomerByIDDAO;
import it.unipd.dei.webapp.database.GetCustomerDAO;
import it.unipd.dei.webapp.database.UpdateCustomerAddressDAO;
import it.unipd.dei.webapp.resource.CartItem;


import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class CartServlet extends AbstractServlet {

    private List<CartItem> cartItems;

    @Override
    public void init() throws ServletException {
        super.init();
        cartItems = new ArrayList<>();  // Initialize the list in the init() method
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String op = req.getRequestURI();
        // URL : ***.com/cart/view
        // URL : ***.com/cart/address
        // URL : ***.com/cart/payment
        // URL : ***.com/cart/invoice
        op = op.substring(op.lastIndexOf("cart") + 5);

        try {
            switch (op) {
                case "view/":
                    doViewCart(req, res);
                    break;
                case "address/":
                    doAddress(req, res);
                    break;
                case "invoice/":
                    doFinalizeInvoice(req, res);
                    break;
                case "payment/":
                    req.getRequestDispatcher("/jsp/cart-payment.jsp").forward(req, res);
                    break;
                default:
                    ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                    writeError(res, ec);
                    logger.error("stacktrace:", "Bad Request!");
            }
        } catch (IOException e) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            writeError(res, ec);
            logger.error("stacktrace:", e);
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String op = req.getRequestURI();
        // URL : ***.com/cart/view
        // URL : ***.com/cart/invoice
        op = op.substring(op.lastIndexOf("cart") + 5);

        if (op.equals("payment/")) {
            doPayment(req, res);
        }
        else {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            writeError(res, ec);
            logger.error("stacktrace:", "Bad request!");
        }
    }


    private void doViewCart(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if(req.getSession().getAttribute("cart") != null) {
            CartItem cart = (CartItem) req.getSession().getAttribute("cart");
        try {
            if (cart.getItems().isEmpty()) {
                req.setAttribute("message", "Your cart is empty!");
                req.getRequestDispatcher("/jsp/cart-view.jsp").forward(req, res);
            } else {
                req.setAttribute("cart", cart);
                req.setAttribute("message", "Cart retrieved successfully.");
                req.getRequestDispatcher("/jsp/cart-view.jsp").forward(req, res);
            }
        } catch (ServletException | IOException e) {
            System.out.println(e);
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }
        }
        else {
            try{
            req.setAttribute("message", "Your cart is empty!");
            req.getRequestDispatcher("/jsp/cart-view.jsp").forward(req, res);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void doAddress(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            if (req.getSession().getAttribute("customer") != null) {
                Customer customer =  (Customer) req.getSession().getAttribute("customer");
                String address = (String) req.getSession().getAttribute("address");
                if (address == null) {
                    req.setAttribute("message", "There is NO saved address for the user.");
                    req.getRequestDispatcher("/jsp/cart-address.jsp").forward(req, res);
                } else {
                    req.setAttribute("address", address);
                    req.setAttribute("message", "There is a saved address for the user.");
                    req.getRequestDispatcher("/jsp/cart-address.jsp").forward(req, res);
                }
            } else {
                req.setAttribute("message", "Please Log in first");
                req.getRequestDispatcher("/jsp/customers/login.jsp").forward(req, res);
            }
        } catch (ServletException e) {
            System.out.println(e);
            writeError(res, ErrorCode.USER_NOT_FOUND);
        }
    }


    private void doPayment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            System.out.println(req.getSession().getAttribute("customer_id"));
            Customer customer = new GetCustomerByIDDAO(Integer.parseInt((String) req.getSession().getAttribute("customer_id")), getDataSource().getConnection()).getCustomerByID();
            String address = (String) req.getSession().getAttribute("address");
            /*String address = req.getAttribute("address")
                    + req.getParameter("firstname")
                    + req.getParameter("lastname")
                    + req.getParameter("zip")
                    + req.getParameter("state")
                    + req.getParameter("country");*/
            boolean saveAddress = Boolean.getBoolean(req.getParameter("saveAddress"));

            new UpdateCustomerAddressDAO(customer, address, getDataSource().getConnection()).updateCustomerAddress();

            req.setAttribute("address", address);
            req.getRequestDispatcher("/jsp/cart-payment.jsp").forward(req, res);
        } catch (ServletException | IOException e) {
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        } catch (SQLException e) {
            writeError(res, ErrorCode.USER_NOT_FOUND);
        }
    }


    private void doFinalizeInvoice(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CartItem cart = (CartItem) req.getSession().getAttribute("cart");
        String address = req.getParameter("address");

        try {
            if (cart.getItems().isEmpty()) {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                //req.getRequestDispatcher("/jsp/message-page.jsp").forward(req, res);
            } else {
                req.setAttribute("cart", cart);
                req.setAttribute("address", address);
                req.setAttribute("message", "Purchase is done.");
                req.getRequestDispatcher("/jsp/cart-invoice.jsp").forward(req, res);
            }
        } catch (ServletException | IOException e) {
            System.out.println(e);
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }
    }

}