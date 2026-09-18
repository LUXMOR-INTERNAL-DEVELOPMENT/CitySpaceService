package app.dao;

import java.util.List;

import app.entity.User;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(String id);

}

