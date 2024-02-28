package mypack.service;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import mypack.model.UserModel;
import org.bson.Document;

public class UserService {
    public UserModel findUserByEmailAndPassword(String email, String password) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("weatherApp");
        MongoCollection<Document> users = database.getCollection("User");

        Document userDoc = users.find(Filters.and(
                Filters.eq("email", email),
                Filters.eq("password", password)
        )).first();

        if (userDoc != null) {
            return new UserModel(userDoc.getString("email"), userDoc.getString("password"), userDoc.getString("location"));
        }
        return null;
    }
}
