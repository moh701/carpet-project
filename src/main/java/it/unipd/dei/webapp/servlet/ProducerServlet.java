package it.unipd.dei.webapp.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sound.midi.Soundbank;

import it.unipd.dei.webapp.database.*;
import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "ProducerServlet", urlPatterns = "/producers/*")
public class ProducerServlet extends AbstractServlet {
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Producer producer = (Producer) session.getAttribute("producer");
        String action = request.getRequestURI();
        action = action.substring(action.lastIndexOf("producers") + 10);
        switch (action) {
            case"login/":
                try {
                    authenticateProducer(request,response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "update/":
                try {
                    if(producer != null) {
                        updateProducer(request, response);
                    } else {

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "updateProduct/":
                try {
                    if(producer != null) {
                        updateProduct(request, response);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "addProduct/":
                try {
                    if(producer != null) {
                        addProduct(request, response);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete/":
                try {
                    deleteProducer(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "register/":
                try{
                    request.getSession().invalidate();
                    registerProducer(request, response);
                }
                catch(ServletException s){
                    request.getSession().setAttribute("error", "User cannot be added");
                    response.sendRedirect(request.getContextPath() + "/jsp/producers/register.jsp");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "forgetPassword/":
                try {
                    forgetPassword(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                writeError(response, ErrorCode.OPERATION_UNKNOWN);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getRequestURI();
        op = op.substring(op.lastIndexOf("producers") + 10);
        HttpSession session = request.getSession();
        Producer producer = (Producer) session.getAttribute("producer");
            switch (op) {
                case "register/":
                    request.getSession().invalidate();
                    request.getRequestDispatcher("/jsp/producers/register.jsp").forward(request, response);
                    break;
                case "login/":
                    request.getSession().invalidate();
                    request.getRequestDispatcher("/jsp/producers/login.jsp").forward(request, response);
                    break;

                case "list/":
                    try {
                        listProducers(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (NamingException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "forgetPassword/":
                    request.getSession().invalidate();
                    request.getRequestDispatcher("/jsp/producers/forgetPassword.jsp").forward(request, response);
                    break;
                case "update/":
                    if (producer != null) {
                        request.getRequestDispatcher("/jsp/producers/update.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/producers/login/");
                    }
                    break;
                case "updateProduct/":
                    if (producer != null) {
                        try {
                            Product product = new GetProductDAO(Integer.parseInt(request.getParameter("product_id")), getDataSource().getConnection()).getProduct();
                            request.getSession().setAttribute("product", product);
                        } catch (SQLException e) {
                            request.getSession().setAttribute("error", "An error occured, please try again later!");
                            response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
                        }
                        request.getRequestDispatcher("/jsp/producers/updateProduct.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
                    }
                    break;
                case "addProduct/":
                    if (producer != null) {
                        request.getRequestDispatcher("/jsp/producers/addProduct.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
                    }
                    break;
                case "dashboard/":
                    try {
                        List<Product> products = new GetProductByProducerIDDAO(producer.getProducer_id(), getDataSource().getConnection()).getProductByProducerID();
                        request.setAttribute("products", products);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (producer != null) {
                        request.getRequestDispatcher("/jsp/producers/dashboard.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/producers/login/");
                    }
                    break;

                case "logout/":
                    logoutOperations(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                    writeError(response, ErrorCode.OPERATION_UNKNOWN);
                    break;
            }
    }

    public void logoutOperations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.getRequestDispatcher("/jsp/startpage.jsp").forward(request, response);

    }
    private void forgetPassword(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String email = request.getParameter("email");
        int result = new ProducerForgetPasswordDAO(email, getDataSource().getConnection()).producerForgetPassword();
        if (result == 0) {
            request.getSession().invalidate();
            request.getSession().setAttribute("success", "Password reset link sent to your email!");
            request.getRequestDispatcher("/jsp/producers/forgetPassword.jsp").forward(request, response);
        } else {
            request.getSession().invalidate();
            request.getSession().setAttribute("error", "Email address can not be found, please register!");
            request.getRequestDispatcher("/jsp/producers/forgetPassword.jsp").forward(request, response);
        }
    }

    private void registerProducer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String mobileNumber = request.getParameter("mobileNumber");
        String bankAccount = request.getParameter("bankAccount");
        String brand = request.getParameter("brand");
        String address=request.getParameter("address");

        Producer producer = new Producer(name, surname, password, email, mobileNumber, bankAccount,brand,address);
        try {
            int result = new RegisterProducerDAO(getDataSource().getConnection(), producer).registerProducer();
            if (result == -1) {
                request.getSession().setAttribute("error", "Email is already registered, please enter a new email address!");
                request.getRequestDispatcher("/jsp/producers/register.jsp").forward(request, response);
            } else if (result == 0) {
                request.getSession().setAttribute("producer", producer);
                request.getRequestDispatcher("/jsp/producers/dashboard.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Unexpected error occurred!");
                request.getRequestDispatcher("/jsp/producers/register.jsp").forward(request, response);
            }

        } catch (SQLException | NamingException e) {
            request.getSession().setAttribute("error", "Unexpected error occured!");
            request.getRequestDispatcher("/jsp/producers/register.jsp").forward(request, response);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void authenticateProducer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Producer producer = new Producer(null, null, password, email, null, null);
        int result = new AuthenticateProducerDAO(getDataSource().getConnection(), producer).authenticateProducer();
        if (result == 0) {
            request.getSession().invalidate();
            request.getSession().setAttribute("producer", new GetProducerDAO(email, getDataSource().getConnection()).getProducer());
            request.getSession().setAttribute("role", "producer");
            response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
        } else {
            request.getSession().setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/jsp/producers/login.jsp").forward(request, response);
        }
    }
    private void listProducers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {

        List<Producer> producers = new GetAllProducersDAO(getDataSource().getConnection()).getAllProducers();
        request.setAttribute("producers", producers);
        request.getRequestDispatcher("/producers.jsp").forward(request, response);
    }

    private void deleteProducer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        String email = request.getParameter("email");

        int result = new DeleteProducerDAO(getDataSource().getConnection(), email).deleteProducer();

        if (result == 0) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete producer");
        }
    }

    private void updateProducer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        Producer producer_old = (Producer) request.getSession().getAttribute("producer");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String mobileNumber = request.getParameter("mobileNumber");
        String bankAccount = request.getParameter("bankAccount");
        String brand = request.getParameter("brand");
        String address = request.getParameter("address");

        Producer producer = new Producer(producer_old.getProducer_id(), name, surname, producer_old.getPassword(), producer_old.getEmail_address(), mobileNumber, bankAccount, address, brand);
        int result = new UpdateProducerDAO(getDataSource().getConnection(), producer).updateProducerNoPassword();

        if (result == 0) {
            System.out.println(result);
            request.getSession().setAttribute("producer", new GetProducerDAO(producer_old.getEmail_address(), getDataSource().getConnection()).getProducer());
            response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update producer");
        }
    }
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        Product product_old = (Product) request.getSession().getAttribute("product");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int no_color = Integer.parseInt(request.getParameter("no_color"));
        String dimension = request.getParameter("dimension");
        String material = request.getParameter("material");
        int price = Integer.parseInt(request.getParameter("price"));
        String category = request.getParameter("category");
        String quality = request.getParameter("quality");
        Product product = new Product(product_old.getProduct_id(), product_old.getProducer_id(), name, product_old.getPicture(), quantity, no_color, dimension, material, price, category, quality);
        int result = new UpdateProductDAO(product, getDataSource().getConnection()).updateProduct();
        System.out.println(result);
        if (result == 0) {
            response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update producer");
        }
    }
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        Producer producer = (Producer) request.getSession().getAttribute("producer");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int no_color = Integer.parseInt(request.getParameter("no_color"));
        String dimension = request.getParameter("dimension");
        String material = request.getParameter("material");
        int price = Integer.parseInt(request.getParameter("price"));
        String category = request.getParameter("category");
        String quality = request.getParameter("quality");
        Product product = new Product(producer.getProducer_id(), name, "", quantity, no_color, dimension, material, price, category, quality);
        int result = new InsertProductDAO(getDataSource().getConnection(), product).insertProduct();
        System.out.println(result);
        if (result == 0) {
            response.sendRedirect(request.getContextPath() + "/producers/dashboard/");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add product");
        }
    }
}

