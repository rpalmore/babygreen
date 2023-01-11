package com.techelevator.dao;

import com.techelevator.model.Profile;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(Long userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String email, String password, String role);

    void editUser(User updatedUser);
}
