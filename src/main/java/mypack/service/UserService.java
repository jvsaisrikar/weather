package mypack.service;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import mypack.model.UserModel;
import org.bson.Document;

public class UserService {
    public UserModel findUserByEmail(String email) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("weatherApp");
            MongoCollection<Document> users = database.getCollection("User");

            Document userDoc = users.find(Filters.eq("email", email)).first();

            if (userDoc != null) {
                return new UserModel(userDoc.getString("email"), userDoc.getString("password"), userDoc.getString("location"));
            }
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return null;
    }
}
