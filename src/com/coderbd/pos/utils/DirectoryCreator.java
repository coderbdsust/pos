/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import java.io.File;

/**
 *
 * @author Biswajit Debnath
 */
public class DirectoryCreator {

    public void makeDir(String directory) {
        File theDir = new File(directory);

        if (!theDir.exists()) {
            System.out.println("creating directory: " + directory);
            boolean result = false;

            try {
                theDir.mkdir();
                System.out.println("Directory Created!");
            } catch (SecurityException se) {
                System.out.println(se.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            System.out.println(directory + " Already Created!");
        }
    }

}
