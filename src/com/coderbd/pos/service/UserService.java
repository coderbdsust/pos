/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.UsersDao;
import com.coderbd.pos.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("userService")
public class UserService {
    
    @Autowired
    private UsersDao usersDao;
    
    public User getUser(String username, String password) {
        return usersDao.getUser(username, password);
    }
    
    public User getUser(String usernameOrMobile){
        return usersDao.getUser(usernameOrMobile);
    }
    
    public boolean createUser(User user){
        return usersDao.createUser(user);
    }

    public List<User> getUsers(String authority) {
       return usersDao.getUsers(authority);
    }

}
