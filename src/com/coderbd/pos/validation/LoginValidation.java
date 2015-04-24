/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coderbd.pos.validation;

/**
 *
 * @author Biswajit Debnath
 */
public class LoginValidation {
    
    public boolean loginValidation(String username, String password){
        if(username==null || username.equals("")) return false;
        if(password==null || password.equals("")) return false;
        return true;
    }
    
}
