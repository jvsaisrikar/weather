package mypack.service;

import mypack.model.UserModel;

public interface UserService {
    void saveUser(UserModel user);
    UserModel getUserByEmail(String email);
}
