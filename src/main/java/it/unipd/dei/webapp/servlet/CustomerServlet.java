package it.unipd.dei.webapp.servlet;
import it.unipd.dei.webapp.database.*;
import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "CustomerServlet", urlPatterns = "/customers/*")
public class CustomerServlet extends AbstractServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getRequestURI();
        op = op.substring(op.lastIndexOf("customers") + 10);
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        switch (op) {
            case "login/":
                request.getSession().invalidate();
                request.getRequestDispatcher("/jsp/customers/login.jsp").forward(request, response);
                break;
            case "list/":
                listCustomers(request, response);
                break;
            case "showForm/":
                showForm(request, response);
                break;
            case "register/":
                request.getSession().invalidate();
                request.getRequestDispatcher("/jsp/customers/register.jsp").forward(request, response);
                break;
            case "forgetPassword/":
                request.getSession().invalidate();
                request.getRequestDispatcher("/jsp/customers/forgetPassword.jsp").forward(request, response);
                break;
            case "dashboard/":
                if (customer != null) {
                    request.getRequestDispatcher("/jsp/customers/dashboard.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/customers/login/");
                }
                break;
            case "update/":
                if (customer != null) {
                    request.getRequestDispatcher("/jsp/customers/update.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/customers/login/");
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
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        String email = request.getParameter("email");

        int result = new DeleteCustomerDAO(email, getDataSource().getConnection()).deleteCustomer();

        if (result == 0) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete producer");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getRequestURI();
        action = action.substring(action.lastIndexOf("customers") + 10);
        switch (action) {
            case "login/":
                try {
                    authenticateCustomer(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete/":
                try {
                    deleteCustomer(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "register/":
                try {
                    request.getSession().invalidate();
                    addCustomer(request, response);
                } catch (ServletException s) {
                    request.getSession().setAttribute("error", "User cannot be added");
                    response.sendRedirect(request.getContextPath() + "/jsp/customers/register.jsp");
                }
                break;
            case "update/":
                try {
                    updateCustomer(request, response);
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

    private void forgetPassword(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String email = request.getParameter("email");
        int result = new CustomerForgetPasswordDAO(email, getDataSource().getConnection()).customerForgetPassword();
        if (result == 0) {
            request.getSession().invalidate();
            request.getSession().setAttribute("success", "Password reset link sent to your email!");
            request.getRequestDispatcher("/jsp/customers/forgetPassword.jsp").forward(request, response);
        } else {
            request.getSession().invalidate();
            request.getSession().setAttribute("error", "Email address can not be found, please register!");
            request.getRequestDispatcher("/jsp/customers/forgetPassword.jsp").forward(request, response);
        }
    }


    private void authenticateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, NamingException, ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Customer customer = new Customer(null, null, password, email, null, null, null);
        int result = new AuthenticateCustomerDAO(customer, getDataSource().getConnection()).authenticateCustomer();

        if (result == 0) {
            //request.getSession().invalidate();
            request.getSession().setAttribute("customer", new GetCustomerDAO(email, getDataSource().getConnection()).getCustomer());
            request.getSession().setAttribute("role", "customer");
            response.sendRedirect(request.getContextPath() + "/customers/dashboard/");
        } else {
            request.getSession().invalidate();
            request.getSession().setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/jsp/customers/login.jsp").forward(request, response);
        }
    }


    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Customer> customers = new GetCustomerListDAO(getDataSource().getConnection()).getCustomerList();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("customer-list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching customers from database", e);
        }
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            ErrorCode ec = ErrorCode.EMAIL_MISSING;
            throw new ServletException("No Email address " + ec);

        } else {
            try {
                Customer customer = new GetCustomerDAO(email, getDataSource().getConnection()).getCustomer();
                if (customer == null) {
                    throw new ServletException("Could not find customer with email: " + email);
                }
                request.setAttribute("customer", customer);
            } catch (SQLException e) {
                throw new ServletException("Error fetching customer from database", e);
            }
        }
        request.getRequestDispatcher("customer-form.jsp").forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String mobileNumber = request.getParameter("mobile_number");

        Customer customer = new Customer(name, surname, password, email, mobileNumber);
        try {
            int result = new RegisterCustomerDAO(customer, getDataSource().getConnection()).registerCustomer();
            if (result == -1) {
                request.getSession().setAttribute("error", "Email is already registered, please enter a new email address!");
                request.getRequestDispatcher("/jsp/customers/register.jsp").forward(request, response);
            } else if (result == 0) {
                request.getSession().setAttribute("email", email);
                request.getRequestDispatcher("/jsp/customers/dashboard.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Unexpected error occurred!");
                request.getRequestDispatcher("/jsp/customers/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Unexpected error occurred!");
            request.getRequestDispatcher("/jsp/customers/register.jsp").forward(request, response);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException {
        Customer customer_old = (Customer) request.getSession().getAttribute("customer");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String mobileNumber = request.getParameter("mobileNumber");
        String bankAccount = request.getParameter("bankAccount");
        String Address = request.getParameter("address");

        Customer c = new Customer(name, surname, customer_old.getEmail_address(), customer_old.getPassword(), mobileNumber, bankAccount, Address);

        int updateResult = new UpdateCustomerNoPasswordDAO(c, getDataSource().getConnection(), customer_old.getEmail_address()).updateCustomerNoPassword();

        if (updateResult == 0) {
            request.setAttribute("message", "Customer updated successfully");
            request.getRequestDispatcher("/customers").forward(request, response);
        } else {
            request.setAttribute("message", "Error updating customer");
            request.getRequestDispatcher("jsp/customer/edit.jsp").forward(request, response);
        }
    }
}


