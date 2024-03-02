package mypack.service;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import mypack.model.UserModel;
import org.bson.Document;

public class UserServiceImpl implements UserService {

    private MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Override
    public void saveUser(UserModel user) {
        try (MongoClient mongoClient = createMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase("weatherApp");
            MongoCollection<Document> users = database.getCollection("User");
            Document newUser = new Document("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("location", user.getLocation());
            users.insertOne(newUser);
        }
    }

    public UserModel getUserByEmail(String email) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("weatherApp");
            MongoCollection<Document> users = database.getCollection("User");

            Document userDoc = users.find(Filters.eq("email", email)).first();

            if (userDoc != null) {
                return new UserModel(userDoc.getString("email"), userDoc.getString("password"), userDoc.getString("location"));
            }
        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                System.err.println("Duplicate key error: A user with this email already exists.");
            } else {
                // other MongoDB write errors
                System.err.println("MongoDB write error: " + e.getMessage());
            }
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return null;
    }
}
