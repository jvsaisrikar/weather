package mypack;

import org.bson.Document;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String location = request.getParameter("location");

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("weatherApp");
        MongoCollection<Document> users = database.getCollection("User");

        // Create a new user document
        Document newUser = new Document()
                .append("email", email)
                .append("password", password)
                .append("location", location);

        // Insert the document into the collection
        users.insertOne(newUser);

        // Close the MongoDB client
        mongoClient.close();

        //TODO handle error
        response.sendRedirect("success.jsp");
		
	}

}
