/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.barutils.main;
//

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Biswajit Debnath
 */
public class POS {

    public static void main(String[] args) {
        BarCodeGeneration barcode = new BarCodeGeneration(300);
        IDBuilder idBuilder = new IDBuilder();
//        int n = 10;
//        int count = 0;
//        while (n-- != 0) {
//            String ID2 = unqId.getUniqueID();
//            System.out.println(ID2.length());
//            barcode.generateBarCodePNG(ID2, ID2);
//            count++;
//        }
//        System.out.println("Count: " + count);
//        

        int gen = 1000;
        while (gen-- != 0) {
            Set set = new HashSet();
            int n = 10000;

            while (n-- != 0) {
                int val = idBuilder.randomID(1000000000, 1999999999);
                set.add(val);
            }
            System.out.println("Generate Unique Number:" + set.size());
        }

    }

}
