package mypack.controller;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import mypack.model.UserModel;
import mypack.service.UserService;
import mypack.service.UserServiceImpl;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static long serialVersionUID = 1L;

    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hashedPassword = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt());
        // Create a new UserModel object from request parameters
        UserModel user = new UserModel(
                request.getParameter("email"),
                hashedPassword,
                request.getParameter("location")
        );

        try {
            userService.saveUser(user);
            response.sendRedirect("success.jsp");
        } catch (Exception e) {
            // Log the exception details as needed
            handleRegistrationException(e, request, response);
        }
    }

    private void handleRegistrationException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (e instanceof MongoWriteException && ((MongoWriteException) e).getError().getCode() == 11000) {
            // Duplicate key error
            request.setAttribute("error", "A user with this email already exists.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } else {
            // General error handling
            request.setAttribute("error", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}