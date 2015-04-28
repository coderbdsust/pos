/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.print;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;

/**
 *
 * @author Biswajit Debnath
 */
public class PosPrinter {
    
    public PosPrinter() {
    }
    

    public void doPrint(ResultSet rs) {
       
        try {
            FileOutputStream os = new FileOutputStream("LK-TL200");
            PrintStream ps = new PrintStream(os);
            ps.println("\n\n\n\n\n\n\n\n");
            ps.println("\t\t\t" + rs.getString("FIRSTNAME") + " " + rs.getString("SURNAME"));
            ps.println("\t\t\t" + rs.getString("ADDRESSLINE1"));
            ps.println("\t\t\t" + rs.getString("ADDRESSLINE2"));
            ps.println("\t\t\t" + rs.getString("ADDRESSLINE3"));
            ps.println("\t\t\t" + rs.getString("ADDRESSLINE4"));
            ps.println("\f");
            ps.close();
        } catch (Exception e) {
            System.out.println("Caught Exception printing mailer.");
        }

    }

}
