package mypack.controller;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import mypack.model.UserModel;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hashedPassword = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt());
        // Create a new UserModel object from request parameters
        UserModel user = new UserModel(
                request.getParameter("email"),
                hashedPassword,
                request.getParameter("location")
        );

        MongoClient mongoClient = null;
        try {
            // Connect to MongoDB
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("weatherApp");
            MongoCollection<Document> users = database.getCollection("User");

            // Create a new user document using the UserModel object
            Document newUser = new Document()
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("location", user.getLocation());

            // Insert the document into the collection
            users.insertOne(newUser);

            // Redirect to the success page
            response.sendRedirect("success.jsp");
        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                // Handle the duplicate key error (e.g., a user with the same email already exists).
                request.setAttribute("error", "A user with this email already exists.");
                response.sendRedirect("error.jsp");
            } else {
                // Handle other write errors
                throw new ServletException(e);
            }
        } catch (Exception e) {
            // General error handling
            response.sendRedirect("error.jsp");
        } finally {
            // closing MongoDB Client
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
}