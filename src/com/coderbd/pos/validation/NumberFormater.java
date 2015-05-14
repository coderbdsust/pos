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
public class NumberFormater {

    public static boolean isInteger(String strValue) {

        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException nfex) {
            System.out.println(nfex.getMessage());
            return false;
        }
    }

    public static boolean isDouble(String strValue) {
        try {
            Double.parseDouble(strValue);
            return true;
        } catch (NumberFormatException nfex) {
            System.out.println(nfex.getMessage());
            return false;
        }
    }
}
