package mypack.model;

public class UserModel {
    private  String email;
    private  String password;
    private String location;

    // Constructor
    public UserModel(String email, String password, String location) {
        this.email = email;
        this.password = password;
        this.location = location;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        location = location.replace(" ", "%20");
        return location;
    }
}
