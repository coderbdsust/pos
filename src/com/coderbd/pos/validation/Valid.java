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
public class Valid {

    public boolean loginValidation(String username, String password) {
        if (username == null || username.equals("")) {
            return false;
        }
        if (password == null || password.equals("")) {
            return false;
        }

        if (username.replace(" ", "").length() != username.length()) {
            return false;
        }
        if (password.replace(" ", "").length() != password.length()) {
            return false;
        }

        return true;
    }

    public static boolean vUsername(String username) {
        if (username == null || username.equals("")) {
            return false;
        }

        boolean alpha = false;
        boolean other = false;

        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                alpha = true;
            } else {
                other = true;
                break;
            }
        }
        return (alpha & !other);
    }

    public static boolean vPassword(String password) {
        if (password == null || password.equals("")) {
            return false;
        }
        if (password.replace(" ", "").length() != password.length()) {
            return false;
        }

        boolean number = false;
        boolean alpha = false;
        boolean other = false;

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                alpha = true;
            } else if (ch >= '0' && ch <= '9') {
                number = true;
            } else {
                other = true;
                break;
            }

        }
        return (number & alpha & !other);
    }

    public static boolean vMobile(String mobile) {
        if (mobile == null || mobile.equals("")) {
            return false;
        }

        try {
            long number = Long.parseLong(mobile);
            System.out.println("Parsed Mobile String!");
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
            return false;
        }
    }

    public static boolean vName(String name) {

        if (name == null || name.equals("")) {
            return false;
        }

        name = name.replace("  ", " ");

        boolean alpha = false;

        boolean invalidCharacter = false;

        for (int i = 0; i < name.length(); i++) {

            char ch = name.charAt(i);

            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                alpha = true;
            } else if (ch == ' ' || ch == '-') {

            } else {
                invalidCharacter = true;
                break;
            }
        }
        return (alpha & !invalidCharacter);
    }

}
