package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> listAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean createUser(String username, String password);

    User getUserByUserId(int id);
}
