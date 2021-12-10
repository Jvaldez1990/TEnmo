package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.listAll();
    }

//    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
//    public User findByUsername (@RequestParam(value="username") String username) {
//        return userDao.findByUsername(username);
//    }

    @RequestMapping(path="/users/{id}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable int id) {
        return userDao.getUserByUserId(id);
    }
}
