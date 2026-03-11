package it.unipd.dei.webapp.servlet;
import it.unipd.dei.webapp.utils.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import jakarta.servlet.http.*;
import java.util.Objects;

@WebServlet(name = "AdminServlet", value = "/admin/*")
public class AdminServlet extends AbstractDatabaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getRequestURI();
        //remove everything prior to /user/ (included) and use the remainder as
        //indicator for the required operation
        op = op.substring(op.lastIndexOf("admin") + 6);


        switch (op) {
            case "":
                request.getRequestDispatcher("/jsp/admin/login.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getRequestURI();
        //remove everything prior to /user/ (included) and use the remainder as
        //indicator for the required operation
        op = op.substring(op.lastIndexOf("admin") + 6);


        switch (op) {
            case "/login/":

                loginOperations(request,response);
                break;
        }
    }
    public void loginOperations(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //take from the request, the parameters (email and password)
        String email = req.getParameter("email");
        String password = req.getParameter("password");


        if (email == null || email.equals("")) {
            //the email is null (was not set on the parameters) or an empty string
            //notify this to the user
            ErrorCode ec = ErrorCode.EMAIL_MISSING;
            res.setStatus(ec.getHTTPCode());

            throw new ServletException("No Email address "+ec);

            //we used jsp for the login page: thus we forward the request
            // req.getRequestDispatcher("/jsp/admin/login.jsp").forward(req, res);

        } else if (password == null || password.equals("")) {
            //the password was empty
            ErrorCode ec = ErrorCode.PASSWORD_MISSING;
            throw new ServletException("Error "+ec);



        } else{
            //try to authenticate the user
            boolean email_login = Objects.equals(convertiSHA256(email), "***");
            boolean psw_login = Objects.equals(convertiSHA256(password), "****");

            if(email_login && psw_login){
                HttpSession session = req.getSession();
                session.setAttribute("role","admin");

            }else{
                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                res.setStatus(ec.getHTTPCode());
                throw new ServletException("Error "+ec);

            }



        }

    }

 public static String convertiSHA256(String password) {
     try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         byte[] array = md.digest(password.getBytes(StandardCharsets.UTF_8));
         StringBuilder sb = new StringBuilder();
         for (byte b : array) {
             sb.append(String.format("%02x", b));
         }
         return sb.toString();
     } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
         return null;
     }
 }

}


